package com.example.demo.controller;

import com.example.demo.mapper.StudentMapper;
import com.example.demo.model.Student;
import com.example.demo.model.User;
import com.example.demo.service.PdfExportService;
import com.example.demo.service.UserService;
import com.example.demo.view.PdfView;
import com.lowagie.text.Font;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@ResponseBody
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private UserService userService;

    @GetMapping("/getStudents")
    @ResponseBody
    public ModelAndView getStudent(){
        List<Student> students1 = studentMapper.selectAll();
        logger.info(students1.toString());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("students",students1);
        modelAndView.setViewName("students");
        return modelAndView;
    }

    @GetMapping("user")
    public Map addUser(@RequestParam("user") User user){
        logger.info(user.toString());
        return new HashMap();
    }


    // 导出接口
    @GetMapping("/export/pdf")
    public ModelAndView exportPdf(String userName, String note) {
        // 查询用户信息列表
        List<User> userList = userService.findUsers(userName, note);
        // 定义PDF视图
        View view = new PdfView(exportService());
        ModelAndView mv = new ModelAndView();
        // 设置视图
        mv.setView(view);
        // 加入数据模型
        mv.addObject("userList", userList);
        return mv;
    }

    // 导出PDF自定义
    @SuppressWarnings("unchecked")
    private PdfExportService exportService() {
        // 使用Lambda表达式定义自定义导出
        return (model, document, writer, request, response) -> {
            try {
                // A4纸张
                document.setPageSize(PageSize.A4);
                // 标题
                document.addTitle("用户信息");
                // 换行
                document.add(new Chunk("\n"));
                // 表格，3列
                PdfPTable table = new PdfPTable(3);
                // 单元格
                PdfPCell cell = null;
                // 字体，定义为蓝色加粗
                Font f8 = new Font();
                f8.setColor(Color.BLUE);
                f8.setStyle(Font.BOLD);
                // 标题
                cell = new PdfPCell(new Paragraph("id", f8));
                // 居中对齐
                cell.setHorizontalAlignment(1);
                // 将单元格加入表格
                table.addCell(cell);
                cell = new PdfPCell(new Paragraph("name", f8));
                // 居中对齐
                cell.setHorizontalAlignment(1);
                table.addCell(cell);
                cell = new PdfPCell(new Paragraph("address", f8));
                cell.setHorizontalAlignment(1);
                table.addCell(cell);
                // 获取数据模型中的用户列表
                List<User> userList = (List<User>) model.get("userList");
                for (User user : userList) {
                    document.add(new Chunk("\n"));
                    cell = new PdfPCell(new Paragraph(user.getId() + ""));
                    cell.setHorizontalAlignment(1);
                    table.addCell(cell);
                    cell = new PdfPCell(new Paragraph(user.getName()));
                    cell.setHorizontalAlignment(1);
                    table.addCell(cell);
                    String note = user.getAddress() == null ? "" : user.getAddress();
                    cell = new PdfPCell(new Paragraph(note));
                    cell.setHorizontalAlignment(1);
                    table.addCell(cell);
                }
                // 在文档中加入表格
                document.add(table);
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        };
    }
}
