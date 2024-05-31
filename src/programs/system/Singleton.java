package programs.system;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

/**
 * 抽象シングルトンクラス
 * @param <T> サブクラス
 * @author 田淵勇輝
 * @deprecated 複数取得時にキャストエラーが発生したため解決できるまで非推奨
 */
public abstract class Singleton<T> {
	private static volatile Object instance;
	
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
        if (Objects.isNull(instance) || !clazz.isInstance(instance)) {
            synchronized (Singleton.class) {
                if (Objects.isNull(instance) || !clazz.isInstance(instance)) {
                    try {
                        instance = clazz.getDeclaredConstructor().newInstance();
                    } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                    	throw new RuntimeException("Error creating singleton instance", e);
                    }
                }
            }
        }
        return (T) instance;
    }
}
