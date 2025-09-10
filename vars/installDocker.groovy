// vars/installDocker.groovy
def call() {
    sh """
        set -e
        echo ">>> Updating packages..."
        sudo apt-get update -y

        echo ">>> Installing required packages for Docker..."
        sudo apt-get install -y apt-transport-https ca-certificates curl software-properties-common || true

        echo ">>> Adding Docker GPG key..."
        curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg

        echo ">>> Adding Docker repository..."
        echo "deb [arch=amd64 signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] https://download.docker.com/linux/ubuntu \$(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null

        echo ">>> Installing Docker..."
        sudo apt-get update -y
        sudo apt-get install -y docker-ce docker-ce-cli containerd.io

        echo ">>> Verifying Docker installation..."
        docker --version
        sudo systemctl status docker
    """
}
