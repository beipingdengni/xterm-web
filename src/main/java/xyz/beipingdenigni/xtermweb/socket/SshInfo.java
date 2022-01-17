package xyz.beipingdenigni.xtermweb.socket;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.Session;
import lombok.Data;

/**
 * @program:
 * @package xyz.beipingdenigni.xtermweb.socket
 * @description: SshInfo
 * @author: tianbeiping1
 * @Date: 2022/1/15 7:25 下午
 **/
@Data
public class SshInfo {

    private String key;
    private Session session;
    private Channel channel;

}
