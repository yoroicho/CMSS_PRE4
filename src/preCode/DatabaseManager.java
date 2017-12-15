/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package preCode;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 * <p>
 * JavaSE用データベースマネージャ<br>
 * JPAを使うオブジェクト指向データベース操作ライブラリ
 * </p>
 * 次のようなメソッドがある<br>
 * <ul>
 * <li>エンティティの新規保存、更新、削除、検索処理、件数取得</li>
 * <li>開始位置、取得件数、並び順を指定したselectクエリ</li>
 * <li>任意のselectクエリ</li>
 * <li>全件数の取得</li>
 * </ul>
 *
 * @author 川場隆、2016.10
 * @see <a href="http://k-webs.jp/">わかりやすいJava サポートページ</a>
 */
public class DatabaseManager<T> {
	private String puName = "default_pu";

	private Class<T> type;
	private EntityManagerFactory emf;
	//private EntityManager em;

	/**
	 * コンストラクタ(2)<br>
	 * エンティティの型と永続性ユニット名を指定して生成する<br>
	 * （例） Databasemanager dm = new DatabaseManager(Employee.class, "employee_pu");
	 *
	 * @param type
	 *            エンティティの型
	 * @param pu
	 *            永続性ユニット名
	 */
	public DatabaseManager(Class<T> type, String pu) {
		this.type = type;
		emf = Persistence.createEntityManagerFactory(pu);
		//em = emf.createEntityManager();

	}

	/**
	 * コンストラクタ(1) <br>
	 * エンティティの型を指定して生成する<br>
	 * 永続性ユニット名は default_pu になる
	 * （例） Databasemanager dm = new DatabaseManager(Employee.class);
	 *
	 * @param type
	 *            エンティティの型
	 */
	public DatabaseManager(Class<T> type) {
		this.type = type;
		System.out.println("★ pu=" + puName);
		emf = Persistence.createEntityManagerFactory(puName);
		//em = emf.createEntityManager();

	}

	/**
	 * エンティティを新規に保存する
	 *
	 * @param emp
	 *            保存するエンティティ
	 * @return データベースマネージャーの参照
	 */
	public DatabaseManager<T> persist(T emp) {
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();
			em.persist(emp);
			em.getTransaction().commit();
		} catch (Exception e) {
			System.err.println("★persist-error\n");

		} finally {
			if(em != null) {
				em.close();
			}
		}
		return this;

	}

	/**
	 * エンティティを更新して保存する
	 *
	 * @param emp
	 *            保存するエンティティ
	 * @return データベースマネージャーの参照
	 */
	public DatabaseManager<T> merge(T emp) {
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();
			em.merge(emp);
			em.getTransaction().commit();
		} catch (Exception e) {
			System.err.println("★merge-error\n");

		} finally {
			if(em != null) {
				em.close();
			}
		}
		return this;
	}

	/**
	 * 主キーを指定してエンティティを削除する
	 *
	 * @param key
	 *            主キーをセットしたエンティティ
	 */
	public void remove(Object key) {
		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();
			T obj = em.find(type, key);
			em.remove(obj);
			em.getTransaction().commit();
		} catch (Exception e) {
			System.err.println("★remove-error\n");

		} finally {
			if(em != null) {
				em.close();
			}
		}
	}

	/**
	 * 主キーを指定してエンティティを検索する
	 *
	 * @param key
	 *            主キーをセットしたエンティティ
	 * @return 検索結果のエンティティ。見つからなければnullを返す
	 */
	public T find(Object key) {
		EntityManager em = null;
		T obj = null;
		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();
			obj = em.find(type, key);
			em.getTransaction().commit();
		} catch (Exception e) {
			System.err.println("★find-error\n");

		} finally {
			if(em != null) {
				em.close();
			}
		}
		return obj;
	}

	/**
	 * 全件取得
	 *
	 * @return 全エンティティのリスト（List）
	 */
	public List<T> getAll() {
		List<T> ls = null;
		String queryString = "SELECT c FROM " + type.getSimpleName() + " c";

		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();

			ls = em.createQuery(queryString, type).getResultList();

			em.getTransaction().commit();

		} catch (Exception e) {
			System.err.println("★getAll()-error\n" + "Query=" + queryString);

		} finally {
			if(em != null) {
				em.close();
			}
		}

		return ls;
	}

	/**
	 * 指定したフィールドの昇順に並び替えて全件取得
	 *
	 * @param orderItem
	 *            並び順のキーにするフィールド名
	 * @return 全エンティティのリスト（List）
	 */
	public List<T> getAll(String orderItem) {

		List<T> ls = null;
		String queryString = "SELECT c FROM " + type.getSimpleName() + " c ORDER BY c." + orderItem;

		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();

			ls = em.createQuery(queryString, type).getResultList();

			em.getTransaction().commit();

		} catch (Exception e) {
			System.err.println("★getAll(String orderItem)-error\n" + "Query=" + queryString);

		} finally {
			if(em != null) {
				em.close();
			}
		}

		return ls;

	}

	/**
	 * 指定したフィールドの昇順に並び替えて全件取得。
	 * descにtrueを指定すると降順になる
	 *
	 * @param orderItem
	 *            並び順のキーにするフィールド名
	 * @param desc
	 *            降順にするかどうか
	 * @return 検索結果のリスト
	 */
	public List<T> getAll(String orderItem, boolean desc) {
		String sort = desc ? " desc" : " asc";
		List<T> ls = getAll(orderItem + sort);
		return ls;
	}

	/**
	 * 開始位置（from）と最大取得件数（max)を指定して取得する
	 *
	 * @param from
	 *            開始位置（0オリジン）
	 * @param max
	 *            最大取得する件数
	 * @return 取得したエンティティのリスト（List）
	 */
	public List<T> get(int from, int max) {

		List<T> ls = null;
		String queryString = "SELECT c FROM " + type.getSimpleName() + " c";

		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();

			ls = em.createQuery(queryString, type).setFirstResult(from).setMaxResults(max).getResultList();

			em.getTransaction().commit();

		} catch (Exception e) {
			System.err.println("★get(int from, int max)-error\n" + "Query=" + queryString);

		} finally {
			if(em != null) {
				em.close();
			}
		}

		return ls;

	}

	/**
	 * 開始位置（from）と最大取得件数（max)、および並び替えのキーにする項目を指定して取得する
	 *
	 * @param from
	 *            開始位置（0オリジン）
	 * @param max
	 *            最大取得する件数
	 * @param orderItem
	 *            並び替えのキーにするフィールド名
	 * @return 検索結果のリスト
	 */
	public List<T> get(int from, int max, String orderItem) {

		List<T> ls = null;
		String queryString = "SELECT c FROM " + type.getSimpleName() + " c ORDER BY c." + orderItem;

		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();

			ls = em.createQuery(queryString, type).setFirstResult(from).setMaxResults(max).getResultList();

			em.getTransaction().commit();

		} catch (Exception e) {
			System.err.println("★get(int from, int max, String orderItem)-error\n" + "Query=" + queryString);

		} finally {
			if(em != null) {
				em.close();
			}
		}

		return ls;

	}

	/**
	 * 開始位置（from）と最大取得件数（max)、および並び替えのキーにする項目を指定して取得する
	 * descにtrueを指定すると降順になる
	 *
	 * @param from
	 *            開始位置（0オリジン）
	 * @param max
	 *            最大取得する件数
	 * @param orderItem
	 *            並び替えのキーにするフィールド名
	 * @param desc
	 *            降順にするかどうか
	 * @return 検索結果のリスト
	 */
	public List<T> get(int from, int max, String orderItem, boolean desc) {
		String sort = desc ? " desc" : " asc";
		List<T> ls = get(from, max, orderItem + sort);
		return ls;

	}

	/**
	 * クエリーを実行してエンティティをのリストを得る
	 *
	 * @param queryString
	 *            JPQLで書いたクエリ
	 * @return 検索結果のリスト
	 */
	public List<T> select(String queryString) {

		List<T> ls = null;

		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();

			ls = em.createQuery(queryString, type).getResultList();

			em.getTransaction().commit();

		} catch (Exception e) {
			System.err.println("★select(String queryString)-error\n" + "Query=" + queryString);

		} finally {
			if(em != null) {
				em.close();
			}
		}

		return ls;

	}

	/**
	 * パラメータ付きのクエリーを指定してエンティティのリストを得る
	 *
	 * @param queryString
	 *            パラメータ付きクエリ
	 * @param param
	 *            パラメータにセットするオブジェクトの配列
	 * @return エンティティのリスト
	 */
	public List<T> select(String queryString, Object... param) {

		EntityManager em = null;
		List<T> ls = null;

		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();

			TypedQuery<T> q = em.createQuery(queryString, type);
			for(int i = 0; i < param.length; i++) {
				q.setParameter(i + 1, param[i]);
			}
			ls = q.getResultList();

			em.getTransaction().commit();

		} catch (Exception e) {
			System.err.println("★select(String queryString)-error\n" + "Query=" + queryString);

		} finally {
			if(em != null) {
				em.close();
			}
		}

		return ls;

	}

	/**
	 * クエリーを指定してfrom件目から最大max件取得する(並び順はクエリーの中に指定)
	 *
	 * @param queryString
	 *            JPQLで書いたクエリ
	 * @param from
	 *            開始位置（0オリジン）
	 * @param max
	 *            最大取得する件数
	 * @return 検索結果のリスト
	 */
	public List<T> select(String queryString, int from, int max) {

		List<T> ls = null;

		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();

			ls = em.createQuery(queryString, type).setFirstResult(from).setMaxResults(max).getResultList();

			em.getTransaction().commit();

		} catch (Exception e) {
			System.err.println("★select(String queryString, int from, int max)-error\n" + "Query=" + queryString);

		} finally {
			if(em != null) {
				em.close();
			}
		}

		return ls;

	}

	/**
	 * パラメータ付きのクエリーを指定してfrom件目から最大max件取得する(並び順はクエリーの中に指定)
	 *
	 * @param queryString
	 *            JPQLで書いたクエリ
	 * @param from
	 *            開始位置（0オリジン）
	 * @param max
	 *            最大取得する件数
	 * @param param
	 *            パラメータにセットするオブジェクトの配列
	 * @return 検索結果のリスト
	 */
	public List<T> select(String queryString, int from, int max, Object... param) {

		List<T> ls = null;

		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();

			TypedQuery<T> q = em.createQuery(queryString, type);
			for(int i = 0; i < param.length; i++) {
				q.setParameter(i + 1, param[i]);
			}
			ls = q.setFirstResult(from).setMaxResults(max).getResultList();

			em.getTransaction().commit();

		} catch (Exception e) {
			System.err.println("★select(String queryString, int from, int max)-error\n" + "Query=" + queryString);

		} finally {
			if(em != null) {
				em.close();
			}
		}

		return ls;

	}

	/**
	 * 保存されている全エンティティの件数を取得する
	 *
	 * @return 全件数
	 */
	public int count() {

		String queryString = "SELECT count(c) FROM " + type.getSimpleName() + " c";
		List<Object> ls = null;
		EntityManager em = null;

		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();

			ls = em.createQuery(queryString, Object.class).getResultList();

			em.getTransaction().commit();

		} catch (Exception e) {
			System.err.println("★count()-error\n" + "Query=" + queryString);

		} finally {
			if(em != null) {
				em.close();
			}
		}
		Object obj = ls.get(0);
		return Integer.valueOf(obj.toString());

	}

	/**
	 * データベースマネージャーを閉じる<br>
	 * プログラムの最後に実行する（毎回は不要）
	 */
	public void close() {
		emf.close();
	}

}