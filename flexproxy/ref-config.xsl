<?xml version="1.0" encoding="gb2312"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/TR/WD-xsl">
    <xsl:template match="/">
    	<html>
    	    <head><title>映射表</title></head>
    	    <body>
    	        <div align="center"><p>映射表</p></div>
    	    <xsl:apply-templates select="*/reflection"/>
    	    </body>
    	</html>
    </xsl:template>
    
    <xsl:template match="*/reflection">
        <p align="center">
        <table border="1">
            <tr>
                <td>映射名</td>
    	        <td>描述</td>
    	        <td>参数</td>
            </tr>
            <xsl:for-each select="*/ref-method">
                <tr>
                    <td><xsl:value-of select="ref-name"/></td>
                    <td><xsl:value-of select="ref-des"/></td>
			    <xsl:for-each select="ref-param/param">
				    <td><xsl:value-of select="@name"/> :<b><xsl:value-of select="@type"/></b></td>
			    </xsl:for-each>
                </tr>
            </xsl:for-each>
        </table>
        </p>
    </xsl:template>

</xsl:stylesheet>
