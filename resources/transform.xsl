<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="xml" indent="yes"/>
    <xsl:key name="type" match="OldCard" use="@type"/>

    <xsl:template match="/">
        <root>
            <xsl:for-each select="OldCardCollection/OldCard[generate-id() = generate-id(key('type', @type)[1])]">
                <xsl:element name="{@type}">
                    <xsl:for-each select="key('type', @type)">
                        <OldCard>
                            <Thema><xsl:value-of select="Thema"/></Thema>
                        </OldCard>
                    </xsl:for-each>
                </xsl:element>
            </xsl:for-each>
        </root>
    </xsl:template>
</xsl:stylesheet>
