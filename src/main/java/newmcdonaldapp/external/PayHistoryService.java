
package newmcdonaldapp.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;

@FeignClient(name="pay", url="${api.url.pay}")
public interface PayHistoryService {

    @RequestMapping(method= RequestMethod.POST, value = "/payHistories", consumes = "application/json")
    public void payRequest(@RequestBody PayHistory payHistory);

    @RequestMapping(method= RequestMethod.GET, path="/payHistories")
    public void payCancel(PayHistory payHistory);
}