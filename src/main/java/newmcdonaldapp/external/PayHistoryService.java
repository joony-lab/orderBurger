
package newmcdonaldapp.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;

@FeignClient(name="pay", url="http://pay:8080")
public interface PayHistoryService {

    @RequestMapping(method= RequestMethod.GET, path="/payHistories")
    public void payRequest(@RequestBody PayHistory payHistory);

    void payCancel(PayHistory payHistory);
}