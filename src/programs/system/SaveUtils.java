package programs.system;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import programs.data.SaveData;

/**
 * セーブデータのセーブ・ロードなどを行うユーティリティクラス
 * @author 田淵勇輝
 */
public class SaveUtils {
	// 使用する暗号化アルゴリズム
	private static final String ALGORITHM = "AES/GCM/NoPadding";
	// 使用する暗号化アルゴリズムの名前
	private static final String ALGORITHM_NAME = "AES";
	// 暗号鍵のサイズ(ビット)
	private static final int KEY_SIZE = 256;
	// 認証タグの長さ(ビット)
	private static final int TAG_LENGTH_BIT = 128;
	// 初期化ベクトルのサイズ(バイト)
	private static final int IV_SIZE = 12;
	// プロジェクトのパス
	private static final String PROJECT_PATH = System.getProperty("user.dir");
	// セーブデータのディレクトリ
	private static final String SAVE_DIRECTORY = File.separator + "save";
	// 暗号鍵のファイル
	private static final String KEY_FILE = File.separator + "savedatakey.key";
	// セーブデータのファイル
	private static final String SAVE_FILE = File.separator + "save.dat";
	
	private static ObjectMapper objectMapper = new ObjectMapper();
	
	/**
	 * セーブデータのセーブ
	 * @param data セーブするデータ
	 */
	public static void encryptAndSave(SaveData data) {
		File directory = new File(PROJECT_PATH + SAVE_DIRECTORY);
		File saveFile = new File(PROJECT_PATH + SAVE_DIRECTORY + SAVE_FILE);
		
		if(!directory.exists()) {
			// saveディレクトリがなければ作成
			directory.mkdir();
		}
		
		SecretKey key;
		// 暗号鍵を生成
		try {
			key = generateKey();
			outputKey(key);
		} catch(NoSuchAlgorithmException | IOException e) {
			e.printStackTrace();
			// 例外が発生したら処理を終了
			return;
		}
		
		
		byte[] iv = new byte[IV_SIZE];
		SecureRandom sr = new SecureRandom();
		sr.nextBytes(iv);
		
		Cipher cipher;
		try {
			cipher = Cipher.getInstance(ALGORITHM);
			GCMParameterSpec parameterSpec = new GCMParameterSpec(TAG_LENGTH_BIT, iv);
			cipher.init(Cipher.ENCRYPT_MODE, key, parameterSpec);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException e) {
			e.printStackTrace();
			// 例外が発生したら処理を終了
			return;
		}
		
		byte[] jsonData;
		byte[] encryptedData;
		
		try {
			// JSON形式に変換
			jsonData = objectMapper.writeValueAsBytes(data);
			// 暗号化
			encryptedData = cipher.doFinal(jsonData);
		} catch (JsonProcessingException | IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
			// 例外が発生したら処理を終了
			return;
		}
		
		try(FileOutputStream fos = new FileOutputStream(saveFile)){
			// save.dat出力
			fos.write(iv);
			fos.write(encryptedData);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * セーブデータのロード
	 * @return セーブデータ
	 */
	public static SaveData decryptAndLoad() {
		File keyFile = new File(PROJECT_PATH + SAVE_DIRECTORY + KEY_FILE);
		File saveFile = new File(PROJECT_PATH + SAVE_DIRECTORY + SAVE_FILE);
		
		if(!keyFile.exists() || !saveFile.exists()) {
			// 暗号鍵かセーブファイルが見つからなければ初期化したデータを返す
			return new SaveData();
		}
		
		SecretKey key;
		try {
			// 暗号鍵を取得
			key = loadKey();
		} catch (IOException e) {
			e.printStackTrace();
			// 例外が発生したらnullを返して処理を終了
			return null;
		}
		
		byte[] fileContent;
		try {
			fileContent = Files.readAllBytes(saveFile.toPath());
		} catch (IOException e) {
			e.printStackTrace();
			// 例外が発生したらnullを返して処理を終了
			return null;
		}
		
		byte[] iv = new byte[IV_SIZE];
		byte[] encryptedData = new byte[fileContent.length - IV_SIZE];
		
		System.arraycopy(fileContent, 0, iv, 0, IV_SIZE);
		System.arraycopy(fileContent, IV_SIZE, encryptedData, 0, encryptedData.length);
		
		Cipher cipher;
		try {
			cipher = Cipher.getInstance(ALGORITHM);
			GCMParameterSpec parameterSpec = new GCMParameterSpec(TAG_LENGTH_BIT, iv);
			cipher.init(Cipher.DECRYPT_MODE, key, parameterSpec);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException e) {
			e.printStackTrace();
			// 例外が発生したらnullを返して処理を終了
			return null;
		}
		
		byte[] decryptedData;
		try {
			// 復号化
			decryptedData = cipher.doFinal(encryptedData);
			return objectMapper.readValue(decryptedData, SaveData.class);
		} catch (IllegalBlockSizeException | BadPaddingException | IOException e) {
			e.printStackTrace();
			// 例外が発生したらnullを返して処理を終了
			return null;
		}
	}
	
	/**
	 * 暗号鍵を生成する
	 * @return 生成された暗号鍵
	 * @throws NoSuchAlgorithmException 
	 */
	private static SecretKey generateKey() throws NoSuchAlgorithmException{
		KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM_NAME);
		keyGen.init(KEY_SIZE);
		return keyGen.generateKey();
	}
	
	/**
	 * 暗号鍵を取得する
	 * @return 取得した暗号鍵
	 * @throws IOException
	 */
	private static SecretKey loadKey() throws IOException {
		File keyFile = new File(PROJECT_PATH + SAVE_DIRECTORY + KEY_FILE);
		byte[] keyBytes = Files.readAllBytes(keyFile.toPath());
		return new SecretKeySpec(keyBytes, ALGORITHM_NAME);
	}
	
	/**
	 * 暗号鍵を出力
	 * @param key 暗号鍵
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private static void outputKey(SecretKey key) throws FileNotFoundException, IOException {
		File keyFile = new File(PROJECT_PATH + SAVE_DIRECTORY + KEY_FILE);
		try (FileOutputStream fos = new FileOutputStream(keyFile)) {
			fos.write(key.getEncoded());
		}
	}
}
