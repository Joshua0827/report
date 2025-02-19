這是過去工作中的一個需求的簡化展示，需求是設計一個Web service接收json，產製對應格式的報表。

使用spring boot + jasper report的組合架設Web service。
報表格式的設計使用Jaspersoft Studio，產制的檔案為.jrxml。

因應需求提供了4個Web API，獨立pdf、獨立word，以及多張合併pdf與多張合併word。
實作時經過考量直接在controller實現即可，不需要過度的設計去區隔邏輯層。
考量新增或修改報表格式，設計方向採取定義好格式檔存放目錄的路徑，可以直接替換或增加，都不需要重啟服務。

可使用Postman測試，若在IDE運行spring boot debug模式是使用預設的8080 port。
若自行打包部署jar檔，需自行複製jrxmls目錄、startup service.bat與stop service.bat等檔案，使用bat檔啟動服務會使用80 port，停止服務也是針對使用80 port的程序終止。

測試用JSON如下

```JSON
{
    "reportType": "PO/PPOB",
    "reportName": "A0001_c114010001",
    "parameters": {
        "CO_NAME": "SAMPLE股份有限公司",
        "DEPT_ADDR": "新北市五股區五工二路",
        "DEPT_TEL": "02-29929090",
        "DEPT_FAX": "02-29929988",
        "SUPP_NO": "A0001",
        "SUPP_NAME": "OO股份有限公司",
        "CO_ZIP": "10900",
        "CO_ADDR": "新北市五股區五權二路",
        "CONTACT1": "Y女士",
        "CO_TEL": "02-22332233",
        "CO_FAX": "02-22332200",
        "PAYMENT": "月結",
        "PO_NO": "c114010001",
        "INVO_USR": "X先生"
    },
    "detailList": [
        {
            "ITEM_NAME": "鍵盤",
            "PO_QTY": "1",
            "UNIT": "個",
            "UNIT_COST": "1000",
            "AMT": "1000",
            "ORDER_NO": "",
            "ITEM_RMK": "代送"
        },
        {
            "ITEM_NAME": "滑鼠",
            "PO_QTY": "1",
            "UNIT": "個",
            "UNIT_COST": "100",
            "AMT": "100",
            "ORDER_NO": "",
            "ITEM_RMK": ""
        }
    ]
}
```