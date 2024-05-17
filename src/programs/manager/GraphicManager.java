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
	
	/**
	 * グラフィックデータのリストのゲッター
	 * @see {@link #graphicDataList}
	 * @return グラフィックデータのリスト
	 */
	public List<GraphicData> getGraphicData() { return graphicDataList; }
	
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for(int i = 0; i < graphicDataList.size(); i++) {
        	transformImage((Graphics2D)g, graphicDataList.get(i));
        }
    }
	
	/**
	 * 画像を変形
	 * アフィン変換を用いた変形を行う
	 * @param g2d グラフィック
	 * @param gd グラフィックデータ
	 */
	private void transformImage(Graphics2D g2d, GraphicData gd) {
		AffineTransform at = g2d.getTransform();
		// ピボットを中心にする
		at.translate(-gd.getPivot().getX() * gd.getScale().getX(), -gd.getPivot().getY() * gd.getScale().getY());
		// 移動
		at.translate(gd.getPosition().getX(), gd.getPosition().getY());
		// 回転軸に移動
		at.translate(gd.getPivot().getX() * gd.getScale().getX(), gd.getPivot().getY() * gd.getScale().getY());
		// 回転
		at.rotate(gd.getRadian());
		// 回転軸から戻す
		at.translate(-gd.getPivot().getX() * gd.getScale().getX(), gd.getPivot().getY() * -gd.getScale().getY());
		// ピボット位置に移動
		at.translate(gd.getPivot().getX(), gd.getPivot().getY());
		// スケーリング
		at.scale(gd.getScale().getX(), gd.getScale().getY());
		// スケーリングに合わせてピボット位置から戻す
		at.translate(-gd.getPivot().getX() * (1 / gd.getScale().getX()), -gd.getPivot().getY() * (1 / gd.getScale().getY()));
		// せん断の中心に移動
		at.translate(gd.getPivot().getX(), gd.getPivot().getY());
		// せん断
		at.shear(gd.getShear().getX(), gd.getShear().getY());
		// せん断の中心から戻す
		at.translate(-gd.getPivot().getX(), -gd.getPivot().getY());
		
		g2d.setTransform(at);
		// 描画
		g2d.drawImage(gd.getImage(), 0, 0, this);
	}
}
