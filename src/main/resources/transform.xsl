<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:param name="groupBy" select="'Type'"/>

    <xsl:output method="xml" indent="yes"/>

    <!-- Define a key for grouping -->
    <xsl:key name="group-key" match="OldCard" use="@*[name() = $groupBy]"/>

    <!-- Template for the root element -->
    <xsl:template match="/OldCardCollection">
        <GroupedOldCards>
            <xsl:for-each select="OldCard[generate-id() = generate-id(key('group-key', @*[name() = $groupBy])[1])]">
                <xsl:element name="{@*[name() = $groupBy]}">
                    <xsl:for-each select="key('group-key', @*[name() = $groupBy])">
                        <xsl:copy-of select="."/>
                    </xsl:for-each>
                </xsl:element>
            </xsl:for-each>
        </GroupedOldCards>
    </xsl:template>

</xsl:stylesheet>
