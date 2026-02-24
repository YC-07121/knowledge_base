##個人知識庫系統 (Personal Knowledge Base)
<hr>
一個基於 Java 與 MySQL 開發的輕量級桌面應用程式，旨在幫助使用者高效組織筆記與知識。透過樹狀層級分類，使用者可以輕鬆管理大量資訊。

###🌟 核心功能
多層級樹狀結構：支援無限層級的分類（Category）與筆記（Note）管理。

全文檢索系統：支援關鍵字搜尋，可同時比對筆記標題與內文。

即時編輯與自動存檔：整合防抖（Debounce）邏輯，停止打字 1.5 秒後自動儲存資料庫。

帳戶管理：具備使用者登入、登出以及密碼修改功能。

###🛠️ 技術棧
開發語言：Java 11

圖形介面：Java Swing (Window Builder)

資料庫：MySQL 8.0

架構模式：MVC (Model-View-Controller) + DAO + Service

資料庫連接：JDBC (PreparedStatement, Try-with-resources)

###🚀 快速開始
####1. 資料庫配置
請在mysqlc匯入檔案根目錄附帶的sql以建立環境：

####2. 環境設定
修改 util.Tool.java 中的資料庫連線資訊：

Java
private static final String URL = "jdbc:mysql://localhost:3306/your_db_name";
private static final String USER = "root";
private static final String PASS = "your_password";
####3. 運行
執行 controller.MainUI 中的 main 方法啟動程式。

####4.測試用登入
帳號 : aaaa  密碼 : aaaa
