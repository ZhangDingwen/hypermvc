package cn.hyperchain.hypermvc.register.jopo;

import java.util.ArrayList;
import java.util.List;

/**
 * @Package cn.hyperchain.hypermvc.register.jopo
 * @Author Martin
 * @Time 17/7/14
 */
public class HyperEntityInfo {
    public String name;
    public String description;
    public String idName;
    public List<String> fieldList = new ArrayList<>();
    public List<String> indexFieldList = new ArrayList<>();
}