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
        wget -q --show-progress --progress=bar:force:noscroll \$MAVEN_URL -O /tmp/\$MAVEN_TAR

        echo ">>> Extracting Maven..."
        sudo mkdir -p /opt
        sudo tar -xzf /tmp/\$MAVEN_TAR -C /opt/
        sudo ln -sfn /opt/apache-maven-\$MAVEN_VERSION \$MAVEN_HOME

        echo ">>> Configuring environment variables..."
        sudo bash -c 'cat > /etc/profile.d/maven.sh << EOF
export M2_HOME=\$MAVEN_HOME
export PATH=\$M2_HOME/bin:\$PATH
EOF'

        echo ">>> Verifying Maven installation..."
        /opt/maven/bin/mvn -version
    """
}
