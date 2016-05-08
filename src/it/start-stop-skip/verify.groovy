import static org.junit.Assert.assertFalse

def file = new File(basedir, "build.log")
assertFalse 'Server should not have started', file.text.contains("FTP server started")
assertFalse 'Should not attempt to stop the server', file.text.contains('FTP server stopped.')
