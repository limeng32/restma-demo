package limeng32.testSpring.web;

import limeng32.testSpring.service.TxService;
import limeng32.testSpring.service.TxService2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping()
public class TxController {

	@Autowired
	private TxService txService;

	@Autowired
	private TxService2 txService2;

	@RequestMapping(value = "/tx")
	public String tx() {
		// txService.getConn();
		txService.insert();
		return "tx";
	}

	@RequestMapping(value = "/tx2")
	public String tx2() {
		// txService.getConn();
		// txService2.insert();
		return "tx";
	}
}
