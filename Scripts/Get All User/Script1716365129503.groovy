import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
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

// Membuat objek request untuk GET
RequestObject request = new RequestObject()
request.setRestRequestMethod("GET")
request.setRestUrl(GlobalVariable.BaseURL + 'api/users?page=2')

// Mengirimkan request GET dan menyimpan responsenya
ResponseObject response = WS.sendRequest(request)

// Memverifikasi status kode respons (200 OK menandakan sukses)
try {
	WS.verifyResponseStatusCode(response, 200)
} catch (AssertionError e) {
	KeywordUtil.markFailed("Expected status code 200, but got: " + response.getStatusCode())
}

// Memverifikasi beberapa nilai di dalam response JSON
try {
	WS.verifyElementPropertyValue(response, 'page', 2)
	WS.verifyElementPropertyValue(response, 'per_page', 6)
	WS.verifyElementPropertyValue(response, 'total', 12)
	WS.verifyElementPropertyValue(response, 'total_pages', 2)
	
	// Contoh verifikasi data pengguna pertama di halaman kedua
	WS.verifyElementPropertyValue(response, 'data[0].id', 7)
	WS.verifyElementPropertyValue(response, 'data[0].first_name', 'Michael')
	WS.verifyElementPropertyValue(response, 'data[0].last_name', 'Lawson')
	WS.verifyElementPropertyValue(response, 'data[0].email', 'michael.lawson@reqres.in')
} catch (AssertionError e) {
	KeywordUtil.markFailed("Response JSON verification failed: " + e.message)
}

// Cetak response untuk pengecekan manual (opsional)
KeywordUtil.logInfo(response.getResponseText())

