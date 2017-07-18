package cn.hyperchain.component;

import com.intellij.openapi.components.ApplicationComponent;
import org.jetbrains.annotations.NotNull;

/**
 * **************************************************************************
 * <p>
 * Copyright (C) 2016 Hyperchain Technologies Co., Ltd. All rights reserved.
 * <p>
 * * ***************************************************************************
 *
 * @Package cn.hyperchain.component
 * @Author Martin
 * @Time 17/7/14
 * @Version 1.0
 * <p>
 * The ??? is used for getting xxx of the xxx.
 * This has methods which uses xxx to do xxx.
 * <p>
 * Thread safe: This class is not thread safe.
 * <p>
 * Change History:<br>
 * <table border=1 CELLPADDING = "3" CELLSPACING = "0">
 * <tr BGCOLOR = "#CCCCFF">
 * <td>Date</td>
 * <td>Author</td>
 * <td>Details</td>
 * </tr>
 * <tr>
 * <td>17/7/14</td>
 * <td>Martin</td>
 * <td>Initial</td>
 * </tr>
 * </table>
 * @See some references
 */
public class GenerateContractComponent implements ApplicationComponent {
    public GenerateContractComponent() {
    }

    @Override
    public void initComponent() {
        // TODO: insert component initialization logic here
    }

    @Override
    public void disposeComponent() {
        // TODO: insert component disposal logic here
    }

    @Override
    @NotNull
    public String getComponentName() {
        return "GenerateContractComponent";
    }

    public void generate(){
// 开启


    }
}
