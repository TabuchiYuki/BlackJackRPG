package programs.controller;

import programs.system.GameObject;

/**
 * メニュー画面のコントローラー
 * @see インターフェース:{@link programs.system.GameObject}
 * @author 田淵勇輝
 */
public class MenuController implements GameObject {
	@Override
	public void init() {
		System.out.print("title");
	}
}
