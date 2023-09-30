package fun.crimiwar.intellstore.util;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.querys.MySqlQuery;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;


/**
 * 代码生成器
 */
public class Generator {


    public static void main(String[] args) {


        MySqlQuery mySqlQuery = new MySqlQuery() {
            @Override
            public String[] fieldCustom() {
                return new String[]{"Default"};
            }
        };


        String url = "jdbc:oracle:thin:@localhost:1521/IntellStore";
        String username = "dadmin";
        String pwd = "31425523";

        /**
         *数据库配置
         */
        DataSourceConfig dbbuild = new DataSourceConfig
                .Builder(url,
                username,
                pwd)
                .dbQuery(mySqlQuery)
                .build();



        AutoGenerator generator = new AutoGenerator(dbbuild);


        /**
         * 全局配置
         */

        String prjpath  = System.getProperty("user.dir");
        String filePath = prjpath + "/src/main/java";

        GlobalConfig globalConfig = new GlobalConfig.Builder()
                .author("MillionDust")
                .outputDir(filePath)
                .commentDate("yyyy-MM-dd")
                .dateType(DateType.TIME_PACK)
                .build();

        /**
         * 包配置
         */
        PackageConfig packageConfig = new PackageConfig.Builder()
                .parent("fun.crimiwar")
                .moduleName("intellstore")
                .entity("domain")
                .mapper("repository")
                .xml("mapper")
                .build();


        /**
         * 模板配置
         */
        TemplateConfig templateConfig = new TemplateConfig.Builder()

                .build();

        /**
         * 注入配置
         */
//        InjectionConfig injectionConfig = new InjectionConfig();


        /**
         * 策略配置
         */
        StrategyConfig strategyConfig = new StrategyConfig.Builder()
                .enableCapitalMode()//开启大写命名
//                .enableSchema()//启用schema
                .addInclude()//添加表匹配，（不写默认库中所有表）


                /*
                开始配置entity
                 */
                .entityBuilder()
                .enableChainModel()//允许链式模型
                .enableLombok()//允许lombok
                .enableRemoveIsPrefix()//开启移除bool类型的is前缀
                .enableTableFieldAnnotation()//开启生成实体时生成字段注解
                .naming(NamingStrategy.underline_to_camel)//数据表映射实体命名策略(默认下划线)，这里转为驼峰命名
                .columnNaming(NamingStrategy.underline_to_camel)//表字段映射实体属性命名规则,这里转为驼峰命名
                .idType(IdType.AUTO)//全局主键id生成策略
                .formatFileName("%s")//格式化文件名
                .build()

                /*
                开始配置mapper
                 */
                .mapperBuilder()
                .enableMapperAnnotation()//开启注解@Mapper生成
                .enableBaseResultMap()//启用xml文件中的BaseResultMap 生成
                .enableBaseColumnList()//启用xml文件中的BaseColumnList
                .formatMapperFileName("%sMapper")//格式化Dao类名称
                .formatXmlFileName("%sMapper")//格式化xml文件名称
                .build()

                /*
                开始配置Service
                 */
                .serviceBuilder()
                .formatServiceFileName("%sService")//格式化 service 接口文件名称
                .formatServiceImplFileName("%sServiceImpl")//格式化 service 接口文件名称
                .build()


                /*
                开始配置controller
                 */
                .controllerBuilder()
                .enableRestStyle()//开启生成@RestController
                .formatFileName("%sController")//格式化文件名称
                .build();


        generator.global(globalConfig)
                .packageInfo(packageConfig)
                .template(templateConfig)
//                .injection()
                .strategy(strategyConfig)
                .execute();
    }


}
