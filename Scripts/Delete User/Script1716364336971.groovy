import com.kms.katalon.core.testobject.RequestObject
import com.kms.katalon.core.testobject.ResponseObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.util.KeywordUtil
import internal.GlobalVariable as GlobalVariable

// Memastikan variabel global sudah ada
try {
	assert GlobalVariable.BaseURL != null : "BaseURL is not set in Global Variables"
} catch (AssertionError e) {
	KeywordUtil.markFailedAndStop("BaseURL is not set in Global Variables: " + e.message)
}

// Membuat objek request untuk DELETE
RequestObject request = new RequestObject()
request.setRestRequestMethod("DELETE")
request.setRestUrl(GlobalVariable.BaseURL + 'api/users/2')

// Mengirimkan request DELETE dan menyimpan responsenya
ResponseObject response = WS.sendRequest(request)

// Memverifikasi status kode respons (204 No Content menandakan sukses)
try {
	WS.verifyResponseStatusCode(response, 204)
} catch (AssertionError e) {
	KeywordUtil.markFailed("Expected status code 204, but got: " + response.getStatusCode())
}

// Cetak response untuk pengecekan manual (opsional)
KeywordUtil.logInfo(response.getResponseText())
