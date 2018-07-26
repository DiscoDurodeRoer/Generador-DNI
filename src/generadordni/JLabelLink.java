/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package generadordni;

import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.JLabel;

/**
 *
 * @author fernandos
 */
public class JLabelLink extends JLabel{

    private String text="";
    private String TextLink=null;
    private URI uri;

    public JLabelLink(){
        super();
        this.setCursor(Cursor.getPredefinedCursor( Cursor.HAND_CURSOR ));
        this.setPreferredSize( new Dimension(34,14) );
        this.setVisible(true);
        //Eventos del raton sobre el JLabel
        addMouseListener(new MouseAdapter() {
            @Override
                public void mouseClicked(MouseEvent e) {
                    Abrir_URL(uri);
                }
            @Override
                public void mouseEntered(MouseEvent e) {
                    setText(text,false);
                }
            @Override
                public void mouseExited(MouseEvent e) {
                    setText(text,true); repaint();
                }
        });

    }

    /**
 * Coloca la dirección web
 */
    public void setLink( String link )
    {        
        try {
            uri = new URI(link);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    /**
 * Coloca el texto que contiene el enlace web
 */
    public void setTextLink( String texto )
    {
        this.TextLink = texto;
    }

    /**
 * Se sobreescribe metodo
 */
    @Override
    public void setText( String value ){                
        setText(  value ,false );
    }

    /**
 * Retorna el texto sin las etiquetas HTML
 */
    public String getTextSinFormato(){
        return text;
    }

    /**
 * Da formato al texto para añadir las etiquetas HTML necesarias
 */
    private void setText(String text, boolean inout){
        //Estilo CSS
        String css = "<style type='text/css'>"
                + ".link {text-decoration: none;font-weight: bold;color:#000000;}"
                + ".link_hover{color:rgb(255,0,0);text-decoration:underline;}"
                + "</style>";

        //estilo css segun el mouse este dentro o fuera
        String clase = (inout)? "link":"link_hover";
        //forma el texto HTML
        String html_text = (TextLink!=null)?text.replace(TextLink, "<span class='"+clase+"' >" +TextLink + "</span>"):text;        
        //coloca al padre
        super.setText("<html>"+ css +"<span>"+ html_text + "<span/></html>");

        this.text = text;
    }

    /**
 * Abre enlace web en el navegador
 */
    private void Abrir_URL(URI uri) {
      if (Desktop.isDesktopSupported()) {
          Desktop desktop = Desktop.getDesktop();
          try {
             desktop.browse(uri);
          } catch (IOException e) {
             System.err.println("Error: No se pudo abrir el enlace" + e.getMessage() );
          }
      } else {
          System.err.println("Error: Error de compatibilidad en la plataforma actual. No se puede abrir enlaces web.");
      }
    }

}
