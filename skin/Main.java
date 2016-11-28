package skin;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        File in = new File("steve");
        if(!in.exists()) {
            in.mkdir();
            return;
        }
        File out = new File("alex");
        if(!out.exists()) {
            if(!out.mkdir()) {
                return;
            }
        }
        for(File file : in.listFiles()) {
            String name = file.getName();
            if(name.endsWith(".png")) {
                System.out.println(name);
                buildAlex(file, new File(out, name.substring(0, name.length() - 3) + "alex.png"));
            }
        }
    }

    public static BufferedImage buildAlex(File in, File out) {
        BufferedImage steve = null;
        try {
            steve = ImageIO.read(in);
        } catch(Exception e) {
            e.printStackTrace();
        }

        BufferedImage alex = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = alex.createGraphics();
        graphics.drawImage(steve, 0, 0, null);

        int dx = 40;
        int dy = 16;
        for(int i = 0; i < 4; i++) {
            graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR, 0.0f));
            Rectangle2D.Double rect = new Rectangle2D.Double(dx, dy, 16, 16);
            graphics.fill(rect);
            graphics.setPaintMode();

            Image up = steve.getSubimage(dx + 4, dy + 0, 4, 4).getScaledInstance(3, 4, Image.SCALE_SMOOTH);
            Image down = steve.getSubimage(dx + 8, dy + 0, 4, 4).getScaledInstance(3, 4, Image.SCALE_SMOOTH);
            Image side0 = steve.getSubimage(dx + 0, dy + 4, 4, 12);
            Image front = steve.getSubimage(dx + 4, dy + 4, 4, 12).getScaledInstance(3, 12, Image.SCALE_SMOOTH);
            Image side1 = steve.getSubimage(dx + 8, dy + 4, 4, 12);
            Image back = steve.getSubimage(dx + 12, dy + 4, 4, 12).getScaledInstance(3, 12, Image.SCALE_SMOOTH);

            graphics.drawImage(up, dx + 4, dy + 0, null);
            graphics.drawImage(down, dx + 7, dy + 0, null);
            graphics.drawImage(side0, dx + 0, dy + 4, null);
            graphics.drawImage(front, dx + 4, dy + 4, null);
            graphics.drawImage(side1, dx + 7, dy + 4, null);
            graphics.drawImage(back, dx + 11, dy + 4, null);

            switch(i) {
            case 1:
                dx = 32;
            case 0:
                dy += 16;
                break;
            default:
                dx += 16;
                break;
            }
        }

        try {
            ImageIO.write(alex, "png", out);
        } catch(IOException e) {
            e.printStackTrace();
        }
        return alex;
    }

}
