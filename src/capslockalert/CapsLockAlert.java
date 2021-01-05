package capslockalert;


import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;


/**
 *
 * @author eCostaRibeiro
 */
public class CapsLockAlert  implements NativeKeyListener {
    final static TrayIcon bandejinha = new TrayIcon(getIcone("image/image.png", "tray icon"),"CapsLockAlert");
    final static PopupMenu popup = new PopupMenu();
    final static SystemTray sysTray = SystemTray.getSystemTray();

    
    public CapsLockAlert() {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException | ClassNotFoundException ex) {
            System.err.println(ex.getMessage());
            System.exit(1);
        }
        
        UIManager.put("swing.boldMetal", Boolean.FALSE);
        SwingUtilities.invokeLater(() -> CapsLockAlert.LigaBandejinha());    
    }
    

    
    
    
    private static void LigaBandejinha() {
        if (!SystemTray.isSupported()) {
            System.out.println("SystemTray is not supported");
            return;
        }
        
        MenuItem btnDesligar = new MenuItem("Desligar");
        popup.add(btnDesligar);
        bandejinha.setPopupMenu(popup);
        bandejinha.setToolTip("CapsLockAlert");
        bandejinha.setImageAutoSize(true);
        
        try {
            sysTray.add(bandejinha);
        } catch (AWTException eX) {
            System.out.println(eX.getMessage());
        }
        
        btnDesligar.addActionListener((ActionEvent e) -> {
            sysTray.remove(bandejinha);
            System.exit(0);
        });
        
        
    }
    
    
    

    final static Image getIcone(String caminho, String altImg) {
        URL caminhoPNG = CapsLockAlert.class.getResource(caminho);
        
        if (caminhoPNG == null) {
            System.err.println("Resource not found: " + caminho);
            return null;
        } else {
            return (new ImageIcon(caminhoPNG, altImg)).getImage();
        }
    }


    @Override
	public void nativeKeyPressed(NativeKeyEvent e) {
                        
		if (e.getKeyCode() == NativeKeyEvent.VC_CAPS_LOCK){
                    bandejinha.displayMessage("Tecla Caps Lock", "pressionada", MessageType.INFO);
                        
		}else{
			System.out.println(NativeKeyEvent.getKeyText(e.getKeyCode()));
		}
	}

    @Override
	public void nativeKeyReleased(NativeKeyEvent e) {
	}

    @Override
	public void nativeKeyTyped(NativeKeyEvent e) {
	}
        
	public static void main(String[] args) {
		try {
			GlobalScreen.registerNativeHook();
		} catch (NativeHookException ex) {
			System.err.println(ex.getMessage());
			System.exit(1);
		}

		// Construct the example object and initialze native hook.
		
                GlobalScreen.addNativeKeyListener(new CapsLockAlert());
	}
    
}
