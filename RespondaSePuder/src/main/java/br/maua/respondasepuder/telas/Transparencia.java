
package br.maua.respondasepuder.telas;

import java.awt.Color;
import javax.swing.JFrame;

public class Transparencia {
    public void aplicarTransparencia(JFrame frame){
        frame.setUndecorated(true);
        frame.setBackground(new Color(0, 0, 0, 0));
    }
}
