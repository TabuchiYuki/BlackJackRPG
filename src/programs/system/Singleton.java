package programs.system;

import java.util.Objects;

/**
 * 抽象シングルトンクラス
 * @param <T> サブクラス
 * @author 田淵勇輝
 */
public abstract class Singleton<T extends Singleton<T>> {
	private static volatile Singleton<?> instance;
	
	/**
	 * protectedコンストラクタ
	 */
	protected Singleton() { }
	
	/**
	 * インスタンスのゲッター
	 * @see {@link instance}
	 * @param <T> サブクラスの型
	 * @param clazz サブクラス
	 * @return インスタンス
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Singleton<T>> T getInstance(Class<T> clazz) {
		if (Objects.isNull(instance)) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    try {
                        instance = clazz.getDeclaredConstructor().newInstance();
                    } catch (Exception e) {
                        throw new RuntimeException("Error creating singleton instance", e);
                    }
                }
            }
        }
        return (T) instance;
	}
}
