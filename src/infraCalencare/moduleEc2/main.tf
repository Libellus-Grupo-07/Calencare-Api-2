# Data source para buscar instâncias existentes com a tag "Name"
data "aws_instances" "existing_instances" {
  filter {
    name   = "tag:Name"
    values = ["private-ec2-01", "private-ec2-02"]
  }
}

# Definição das Instâncias EC2
resource "aws_instance" "public_ec2_backend-1" {
  count = length(data.aws_instances.existing_instances.ids) == 0 ? 1 : 0  # Cria apenas se não existir

  ami               = var.ami
  availability_zone = var.az
  instance_type     = var.inst_type
  ebs_block_device {
    device_name = "/dev/sda1"
    volume_size = 16
    volume_type = "gp3"
  }
  key_name                    = var.key_pair_name
  subnet_id                   = var.subnet_id
  associate_public_ip_address = true # Certifique-se de que está como true
  vpc_security_group_ids      = [var.sg_id]  # Usar o novo SG
  tags = {
    Name = "private-ec2-01"
  }
  user_data = base64encode(<<-EOF
    #!/bin/bash

    # Cria a pasta aws
    sudo mkdir -p /home/ubuntu/aws

    # Verifica se o repositório já foi clonado
    if [ ! -d "/home/ubuntu/aws/.git" ]; then
      sudo git clone https://github.com/Libellus-Grupo-07/Calencare-Api.git /home/ubuntu/aws
      echo "Repositório clonado com sucesso"
    else
      cd /home/ubuntu/aws
      sudo git pull origin main  # Atualiza o repositório
    fi

    # Atualiza pacotes e instala Java
    sudo apt-get update -y
    sudo apt-get install -y default-jdk

    # Instala Docker
    sudo apt-get install -y docker.io

    # Instala Docker Compose
    sudo curl -L "https://github.com/docker/compose/releases/download/1.29.2/docker-compose-\$(uname -s)-\$(uname -m)" -o /usr/local/bin/docker-compose
    sudo chmod +x /usr/local/bin/docker-compose

    # Inicia e habilita Docker
    sudo systemctl start docker
    sudo systemctl enable docker

    # Navega até o diretório do projeto
    cd /home/ubuntu/aws

    # Constrói a imagem Docker usando o Dockerfile
    sudo docker build -t calencare-api .

    # Executa o Docker Compose para iniciar os serviços
    sudo docker-compose up --build -d  # Rodar em segundo plano

    echo "Setup finalizado com sucesso"
    EOF
  )
}

resource "aws_instance" "public_ec2_backend-2" {
  count = length(data.aws_instances.existing_instances.ids) == 0 ? 1 : 0  # Cria apenas se não existir

  ami               = var.ami
  availability_zone = var.az
  instance_type     = var.inst_type
  ebs_block_device {
    device_name = "/dev/sda1"
    volume_size = 16
    volume_type = "gp3"
  }
  key_name                    = var.key_pair_name
  subnet_id                   = var.subnet_id
  associate_public_ip_address = true  # Certifique-se de que está como true
  vpc_security_group_ids      = [var.sg_id]  # Usar o novo SG
  tags = {
    Name = "private-ec2-02"
  }
  user_data = base64encode(<<-EOF
    #!/bin/bash

    # Cria a pasta aws
    sudo mkdir -p /home/ubuntu/aws

    # Verifica se o repositório já foi clonado
    if [ ! -d "/home/ubuntu/aws/.git" ]; then
      sudo git clone https://github.com/Libellus-Grupo-07/Calencare-Api.git /home/ubuntu/aws
      echo "Repositório clonado com sucesso"
    else
      cd /home/ubuntu/aws
      sudo git pull origin main  # Atualiza o repositório
    fi

    # Atualiza pacotes e instala Java
    sudo apt-get update -y
    sudo apt-get install -y default-jdk

    # Instala Docker
    sudo apt-get install -y docker.io

    # Instala Docker Compose
    sudo curl -L "https://github.com/docker/compose/releases/download/1.29.2/docker-compose-\$(uname -s)-\$(uname -m)" -o /usr/local/bin/docker-compose
    sudo chmod +x /usr/local/bin/docker-compose

    # Inicia e habilita Docker
    sudo systemctl start docker
    sudo systemctl enable docker

    # Navega até o diretório do projeto
    cd /home/ubuntu/aws

    # Constrói a imagem Docker usando o Dockerfile
    sudo docker build -t calencare-api .

    # Executa o Docker Compose para iniciar os serviços
    sudo docker-compose up --build -d  # Rodar em segundo plano

    echo "Setup finalizado com sucesso"
    EOF
  )
}

resource "aws_eip_association" "eip_assoc_01" {
  count         = length(aws_instance.public_ec2_backend-1) > 0 ? 1 : 0  # Verifica se a instância existe
  instance_id   = aws_instance.public_ec2_backend-1[0].id
  allocation_id  = "eipalloc-0f2aa5d16ab6481d7" # ID de alocação do EIP
}
