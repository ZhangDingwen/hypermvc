package cn.hyperchain.action;

import cn.hyperchain.component.GenerateContractComponent;
import cn.hyperchain.hypermvc.exception.RegisterException;
import cn.hyperchain.hypermvc.register.EntityRegister;
import com.intellij.javaee.model.xml.Icon;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.richcopy.model.OutputInfoSerializer;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;

import java.io.IOException;

/**
 * **************************************************************************
 * <p>
 * Copyright (C) 2016 Hyperchain Technologies Co., Ltd. All rights reserved.
 * <p>
 * * ***************************************************************************
 *
 * @Package cn.hyperchain.action
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
public class GenerateControllerAction extends DumbAwareAction {

    @Override
    public void actionPerformed(AnActionEvent event) {
        // 获取 ［生成合约代码］ 的组件
        Application application = ApplicationManager.getApplication();
        GenerateContractComponent generateContractComponent = application.getComponent(GenerateContractComponent.class);

        Project project = event.getProject();
        VirtualFile bd = project.getBaseDir();
        VirtualFile entityPackage = bd.findFileByRelativePath("src/cn/hyperchain/business/entity");

        String entityPackagePath = project.getBasePath() + "/" + "src/cn/hyperchain/business/entity";
        if (entityPackage == null) {
            Messages.showErrorDialog(project, "找不到entity包，请确认项目路径" + entityPackagePath, "扫描entity失败！");
            return;
        }

        VirtualFile[] entities = entityPackage.getChildren();
        if (entities == null) {
            Messages.showErrorDialog(project, "entity包下没有实体类，请确认项目路径" + entityPackagePath, "扫描entity失败！");
            return;
        }

        for (VirtualFile entity : entities) {
            System.out.println(entity.getName());
            try {
                EntityRegister.getInstance().register(entityPackagePath + "/" + entity.getName());
            } catch (IOException e) {
                Messages.showErrorDialog(project, "读取entity实体类" + entityPackagePath + "异常:" + e.getMessage(), "扫描entity失败！");
                return;
            } catch (RegisterException e) {
                Messages.showErrorDialog(project, "读取entity实体类" + entityPackagePath + "异常:" + e.getMessage(), "扫描entity失败！");
                return;
            } catch (Exception e) {
                Messages.showErrorDialog(project, "读取entity实体类" + entityPackagePath + "异常:" + e.getMessage(), "扫描entity失败！");
                return;
            }
        }

    }
}
