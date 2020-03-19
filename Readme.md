# RxMicro Documentation and Examples

## Setup Development Environment

### Install the necessary build tools

You must have:

* 64-bit OpenJDK 11 or above from a vendor like [AdoptOpenJDK](https://adoptopenjdk.net/) or [Azul Systems](https://www.azul.com/downloads/zulu-community/);
* [Git](https://git-scm.com/);
* [IntelliJ IDEA](https://www.jetbrains.com/idea/)
    (*RxMicro project team uses IntelliJ IDEA as the primary IDE, although You can use other development environments as long as You adhere to our coding style.*)
* [Apache Maven 3.x](https://maven.apache.org/) 
    (*This tool can be already integrated to your IDE*);
    
### IntelliJ IDEA Configurations

#### Code style

Download [rxmicro-code-styles.xml](../.coding/rxmicro-code-styles.xml) configuration and copy into `<IntelliJ config directory>/codestyles` directory.
(*For example: `/home/${USER-NAME}/.IdeaIC${VERSION}/config/codestyles/`*).
Choose `rxmicro-code-styles` as the default code style.

#### Copyright profile

Download and import [rxmicro-copyright-profile.xml](../.coding/rxmicro-copyright-profile.xml)

*(https://www.jetbrains.com/help/idea/copyright.html)*