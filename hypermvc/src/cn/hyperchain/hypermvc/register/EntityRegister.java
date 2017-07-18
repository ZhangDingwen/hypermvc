package cn.hyperchain.hypermvc.register;

import cn.hyperchain.hypermvc.exception.RegisterException;
import cn.hyperchain.hypermvc.register.constant.RegexString;
import cn.hyperchain.hypermvc.register.jopo.HyperEntityInfo;
import cn.hyperchain.hypermvc.register.jopo.HyperFieldInfo;
import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Package cn.hyperchain.hypermvc.register.jopo
 * @Author Martin
 * @Time 17/7/14
 */
public class EntityRegister {

    private String line;

    private Integer maxInteger = 0;
    private Integer maxFraction = 0;

    public Integer maxExtension = 0;

    public Map<String, HyperEntityInfo> entityName2HyperEntityInfo = new HashMap<>();
    public Map<String, Map<String, HyperFieldInfo>> entityName2FieldName2FieldInfo = new HashMap<>();

    private static EntityRegister ourInstance = new EntityRegister();

    public static EntityRegister getInstance() {
        return ourInstance;
    }

    private EntityRegister() {

    }

    public void register(String entityPath) throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(new File(entityPath)));
        HyperEntityInfo entityInfo = new HyperEntityInfo();
        HyperFieldInfo fieldInfo = null;
        Map<String, HyperFieldInfo> fieldName2FieldInfo = new HashMap<>();
        while ((line = reader.readLine()) != null) {
            // 解析类说明文字
            if (line.contains("@ApiModel(\"")) {
                String entityDescription = getMatch(RegexString.entityDescription);
                if (entityDescription == null)
                    throw new RegisterException("@ApiModel注解无法解读，请严格按下面的格式写注解：@ApiModel(\"XXX\")");

                entityInfo.description = entityDescription;

                System.out.println(entityInfo.description);
                continue;
            }
            // 解析类名
            if (line.contains(" class ") && line.contains("{")) {
                String entityName = getMatch(RegexString.entityName).trim();
                if (!entityName.contains("Entity"))
                    throw new RegisterException("没有以\"Entity\"为命名后缀！");

                entityInfo.name = StringUtils.removeEnd(entityName, "Entity");

                System.out.println(entityInfo.name);
                continue;
            }
            // 解析属性描述
            if (line.contains("@ApiModelProperty(\"")) {
                String entityFieldDescription = getMatch(RegexString.entityFieldDescription);
                if (entityFieldDescription == null)
                    throw new RegisterException("@ApiModelProperty注解无法解读，请严格按下面的格式写注解：@ApiModelProperty(\"XXX\")");

                if (fieldInfo == null)
                    fieldInfo = new HyperFieldInfo();

                if (fieldInfo.description != null)
                    throw new RegisterException("一个属性不能被@ApiModelProperty注解两次！或者请检查是否有属性不是private String形式。");

                fieldInfo.description = entityFieldDescription;

                System.out.println(fieldInfo.description);
                continue;
            }

//            // 解析属性－如果是字符串，获取最大长度
//            if (line.contains("@Length(")) {
//                String max = getMatch(RegexString.max);
//                if (max == null)
//                    throw new RegisterException("@Length注解没有max值！");
//
//                if (fieldInfo == null)
//                    fieldInfo = new HyperFieldInfo();
//
//                if (fieldInfo.max != null)
//                    throw new RegisterException("一个属性不能被@Length注解两次！或者请检查是否有属性不是private String形式。");
//
//                fieldInfo.max = new Integer(max);
//
//                System.out.println(fieldInfo.max);
//                continue;
//            }

            if (line.contains("@Digits(")) {
                String integer = getMatch(RegexString.integer);

                if (integer == null)
                    throw new RegisterException("@Digits注解没有integer值");

                String fraction = getMatch(RegexString.fraction);

                if (fraction == null)
                    throw new RegisterException("@Digits注解没有fraction值");

                if (fieldInfo == null)
                    fieldInfo = new HyperFieldInfo();

                if (fieldInfo.integer != null)
                    throw new RegisterException("一个属性不能被@Digits注解两次！或者请检查是否有属性不是private String形式。");

                if (fieldInfo.fraction != null)
                    throw new RegisterException("一个属性不能被@Digits注解两次！或者请检查是否有属性不是private String形式。");

                fieldInfo.integer = new Integer(integer);
                fieldInfo.fraction = new Integer(fraction);

                if (fieldInfo.fraction == 0 && fieldInfo.integer > 75)
                    throw new RegisterException("整数位数不能超过75位");

                if (fieldInfo.fraction > maxFraction)
                    maxFraction = fieldInfo.fraction;


                if (fieldInfo.fraction > 0 && fieldInfo.integer > maxInteger)
                    maxInteger = fieldInfo.integer;


                System.out.println(fieldInfo.integer);
                System.out.println(fieldInfo.fraction);
                continue;
            }

            // 解析属性是否是主键

            // 解析属性是否索引

            // 解析属性是否外键

            // 解析属性格式：地址／英文
//            if (line.contains("@HyperFieldFormat(")){
//
//            }

            if (line.contains("private String ")) {
                String fieldName = getMatch(RegexString.fieldName);

                if (fieldName == null)
                    throw new RegisterException("属性名格式无法解析，请按照下面格式：private String xxx;");

                if (fieldInfo == null)
                    throw new RegisterException(fieldName + "属性不能没有@ApiModelProperty或@Digits注解！");

                if (fieldInfo.description == null)
                    throw new RegisterException(fieldName + "属性不能没有@ApiModelProperty注解！");

                if (fieldInfo.isId && fieldInfo.isIndex)
                    throw new RegisterException(fieldName + "属性不能是主键且是索引。");

                if (fieldInfo.isId && fieldInfo.isForeignKey)
                    throw new RegisterException(fieldName + "属性不能是主键且是外键。");

                fieldInfo.name = fieldName;
                fieldInfo.entityName = entityInfo.name;

                fieldName2FieldInfo.put(fieldName, fieldInfo);

                fieldInfo = null;
            }


        }
        reader.close();
    }

    public String getMatch(String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);
        if (matcher.find())
            return matcher.group(0);
        else
            return null;
    }


}
