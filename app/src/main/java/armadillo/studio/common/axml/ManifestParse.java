package armadillo.studio.common.axml;


import org.xmlpull.v1.XmlPullParser;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import armadillo.studio.common.axml.xml.decode.AXmlDecoder;
import armadillo.studio.common.axml.xml.decode.AXmlResourceParser;

public class ManifestParse
{
    public static List<String> parseManifestActivity (InputStream is ) throws IOException
    {
        String PackageName = null;
        List<String> list=new ArrayList<String>( );
        AXmlDecoder axml = AXmlDecoder.decode ( is );
        AXmlResourceParser parser = new AXmlResourceParser ( );
        parser.open ( new ByteArrayInputStream( axml.getData ( ) ), axml.mTableStrings );
        int type;
        while ( ( type = parser.next ( ) ) != XmlPullParser.END_DOCUMENT )
        {
            if ( type != XmlPullParser.START_TAG )
                continue;
            if ( parser.getName ( ).equals ( "manifest" ) )
            {
                int size = parser.getAttributeCount ( );
                for ( int i = 0; i < size; ++i )
                {
                    if ( parser.getAttributeName ( i ).equals ( "package" ) )
                        PackageName = parser.getAttributeValue ( i );
                }
            }
            else if ( parser.getName ( ).equals ( "activity" ) )
            {
                int size = parser.getAttributeCount ( );
                for ( int i = 0; i < size; ++i )
                {
                    if ( parser.getAttributeNameResource ( i ) == 0x01010003 )
                    {
                        String name=parser.getAttributeValue ( i );
                        if ( name.startsWith ( "." ) )
                        {
                            name = PackageName + name;
                        }
                        list.add ( name );
                    }
                }
            }
        }
        return list;
    }
}


