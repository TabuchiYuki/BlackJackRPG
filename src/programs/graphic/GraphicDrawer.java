package programs.graphic;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import programs.data.GraphicData;

public class GraphicDrawer extends JPanel {
	private List<GraphicData> graphicDataList = new ArrayList<GraphicData>();
	
	public List<GraphicData> getGraphicData() { return graphicDataList; }
	
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for(int i = 0; i < graphicDataList.size(); i++) {
        	transformImage((Graphics2D)g, graphicDataList.get(i));
        }
    }
	
	private void transformImage(Graphics2D g2d, GraphicData gd) {
		AffineTransform at = g2d.getTransform();
		// ピボット位置に移動
		at.translate((int)gd.getPivot().getX(), (int)gd.getPivot().getY());
		// スケーリング
		at.scale(gd.getScale().getX(), gd.getScale().getY());
		// 回転
		at.rotate(gd.getRadian());
		// ピボット位置から戻す
		at.translate(-(int)gd.getPivot().getX(), -(int)gd.getPivot().getY());
		// 移動
		at.translate((int)gd.getPosition().getX(), (int)gd.getPosition().getY());
		g2d.setTransform(at);
		// 描画
		g2d.drawImage(gd.getImage(), 0, 0, this);
	}
}
