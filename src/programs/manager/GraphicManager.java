package programs.manager;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.swing.JPanel;

import programs.data.ClickEventData;
import programs.data.GraphicData;
import programs.data.TextGraphicData;
import programs.system.SystemValue;

/**
 * グラフィックの描画処理を行うクラス
 * @see スーパークラス:{@link javax.swing.JPanel JPanel}
 * @author 田淵勇輝
 */
public class GraphicManager extends JPanel {
	private static GraphicManager instance;
	
	private List<GraphicData> graphicDataList = new ArrayList<>();
	private List<TextGraphicData> textDataList = new ArrayList<>();
	
	private double frameRate;
	private double originalScaleX;
	private double originalScaleY;
	
	/**
	 * プライベートコンストラクタ
	 */
	private GraphicManager() {
		setPreferredSize(new Dimension(SystemValue.WINDOW_WIDTH, SystemValue.WINDOW_HEIGHT));
	}
	
	/**
	 * インスタンスのゲッター
	 * @see {@link #instance}
	 * @return インスタンス
	 */
	public static GraphicManager getInstance() {
		if(Objects.isNull(instance)) {
			instance = new GraphicManager();
		}
		return instance;
	}
	
	/**
	 * グラフィックデータのリストのゲッター
	 * @see {@link #graphicDataList}
	 * @return グラフィックデータのリスト
	 */
	public List<GraphicData> getGraphicDataList() { return graphicDataList; }
	/**
	 * テキストグラフィックデータのリストのゲッター
	 * @see {@link #textDataList}
	 * @return テキストグラフィックデータのリスト
	 */
	public List<TextGraphicData> getTextDataList() { return textDataList; }
	
	/**
	 * [デバッグ用]フレームレートのセッター
	 * @see {@link #frameRate}
	 * @param frameRate フレームレート
	 */
	public void setFrameRate(double frameRate) { this.frameRate = frameRate; }
	
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        originalScaleX = g2d.getTransform().getScaleX();
        originalScaleY = g2d.getTransform().getScaleY();
        
        graphicDataList.forEach(gd -> {
        	if(gd.isShow()) {
        		transformImage(g2d, gd);
        	}
        });
        
        // テキストの表示
        textDataList.forEach(td -> {
        	Font font = td.getFont();
        	g2d.setFont(font);
        	g2d.setColor(td.getColor());
        	
        	AffineTransform at = g2d.getTransform();
    		// 行列を初期化
        	setOriginalTransform(at);
    		// 移動
    		at.translate(td.getPosition().getX(), td.getPosition().getY());
    		
    		// 中央揃えにする
    		if(td.getCenterAlign()) {
    			FontMetrics fontmetrics = g2d.getFontMetrics();
    			at.translate(-fontmetrics.stringWidth(td.getText()) / 2, 0);
    		}
    		
    		g2d.setTransform(at);
        	g2d.drawString(td.getText(), 0, 0);
        });
        
        // デバッグ表示
        if(SystemValue.DEBUG) {
        	AffineTransform at = g2d.getTransform();
    		// 行列を初期化
        	setOriginalTransform(at);
    		g2d.setTransform(at);
    		
        	Color clickEventCol = new Color(255, 0, 0, 127);
        	g2d.setColor(clickEventCol);
        	Map<String,ClickEventData> clickEventMap = ClickEventManager.getInstance().getClickEventsMap();
        	clickEventMap.forEach((k, v) -> {
        		g2d.fillRect((int) v.getPosition().getX(), (int) v.getPosition().getY(),
        			(int) v.getArea().getX(), (int) v.getArea().getY());
        	});
        	
        	g2d.setColor(Color.WHITE);
        	g2d.setFont(new Font("Arial", Font.PLAIN, 16));
        	String frameRateText;
        	if(Objects.isNull(frameRate) || frameRate == 0.0d) {
        		frameRateText = "FrameRate: measuring...";
        	} else {
        		frameRateText = String.format("FrameRate: %.3f", frameRate);
        	}
        	g2d.drawString(frameRateText, 0, 36);
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
		// 行列を初期化
		setOriginalTransform(at);
		
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
	
	/**
	 * レイヤー番号の順番にソートする
	 */
	public void sortLayer() {
		mergeSort(graphicDataList, 0, graphicDataList.size() - 1);
	}
	
	/**
	 * マージソート
	 * @param data グラフィックデータのリスト
	 * @param left 左のインデックス番号
	 * @param right 右のインデックス番号
	 */
	private void mergeSort(List<GraphicData> data, int left, int right) {
		if(left < right) {
			// リストを分割
			int mid = (left + right) / 2;
			mergeSort(data, left, mid);
			mergeSort(data, mid + 1, right);
			// マージ
			merge(data, left, mid, right);
		}
	}
	
	/**
	 * 分割したリストをマージ
	 * @param data グラフィックデータのリスト
	 * @param left 左のインデックス番号
	 * @param mid 中央のインデックス番号
	 * @param right 右のインデックス番号
	 */
	private void merge(List<GraphicData> data, int left, int mid, int right) {
		int lLen = mid - left + 1;
		int rLen = right - mid;
		
		GraphicData[] L = new GraphicData[lLen];
		GraphicData[] R = new GraphicData[rLen];
		
		for(int i = 0; i < lLen; i++) {
			L[i] = data.get(left + i);
		}
		for(int j = 0; j < rLen; j++) {
			R[j] = data.get(mid + 1 + j);
		}
		
		int i = 0, j = 0;
		int k = left;
		while(i < lLen && j < rLen) {
			if (L[i].getLayer() <= R[j].getLayer()) {
                data.set(k, L[i]);
                i++;
            } else {
            	data.set(k, R[j]);
                j++;
            }
			k++;
		}
		
		while(i < lLen) {
			data.set(k, L[i]);
            i++;
            k++;
		}
		
		while(j < rLen) {
			data.set(k, R[j]);
            j++;
            k++;
		}
	}
	
	/**
	 * アフィン変換を初期化する
	 * @param at アフィン変換
	 * @return 初期化したアフィン変換
	 */
	private void setOriginalTransform(AffineTransform at) {
		at.setToIdentity();
		at.setToScale(originalScaleX, originalScaleY);
	}
}
