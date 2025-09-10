def call(String mavenVersion = "3.9.6") {
    sh """
        set -e
        MAVEN_VERSION=${mavenVersion}
        MAVEN_HOME=/opt/maven
        MAVEN_TAR=apache-maven-\$MAVEN_VERSION-bin.tar.gz
        MAVEN_URL=https://downloads.apache.org/maven/maven-3/\$MAVEN_VERSION/binaries/\$MAVEN_TAR

        echo ">>> Installing required tools..."
        sudo apt-get update -y
        sudo apt-get install -y wget tar

        echo ">>> Downloading Apache Maven \$MAVEN_VERSION..."
        wget -q \$MAVEN_URL -O /tmp/\$MAVEN_TAR

        echo ">>> Extracting Maven..."
        sudo mkdir -p \$MAVEN_HOME
        sudo tar -xzf /tmp/\$MAVEN_TAR -C /opt/
        sudo ln -sfn /opt/apache-maven-\$MAVEN_VERSION \$MAVEN_HOME

        echo ">>> Configuring environment variables..."
        if ! grep -q "M2_HOME" /etc/profile.d/maven.sh 2>/dev/null; then
            echo "export M2_HOME=\$MAVEN_HOME" | sudo tee /etc/profile.d/maven.sh
            echo "export PATH=\\\$M2_HOME/bin:\\\$PATH" | sudo tee -a /etc/profile.d/maven.sh
        fi
        source /etc/profile.d/maven.sh

        echo ">>> Verifying Maven installation..."
        \$MAVEN_HOME/bin/mvn -version
    """
}
