import static org.junit.Assert.assertTrue

Properties properties = new Properties()
def portsFile = new File(basedir, "target/ftp/ports.txt")
portsFile.withInputStream {
    properties.load(it)
}

def ftpRootDir = new File(basedir, "target/ftpserver")
def file = new File(basedir, "build.log")
assertTrue "Ftp Server should have started server on port ${properties['ftp.port']}", file.text.contains("FTP server started on port ${properties['ftp.port']}")
assertTrue "FTP server root should be ${ftpRootDir.absolutePath}", file.text.contains("FTP server root is ${ftpRootDir.absolutePath}")

assertTrue 'Shutdown should have been invoked', file.text.contains("FTP server stopped.")
