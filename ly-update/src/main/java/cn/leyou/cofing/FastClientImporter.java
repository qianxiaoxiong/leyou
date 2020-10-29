package cn.leyou.cofing;

import com.github.tobato.fastdfs.FdfsClientConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.Import;
import org.springframework.jmx.support.RegistrationPolicy;

import java.lang.annotation.Retention;

@Configuration
@Import(value = FdfsClientConfig.class)
//解决jmx重复配置
@EnableMBeanExport( registration = RegistrationPolicy.IGNORE_EXISTING)
public class FastClientImporter {
}
