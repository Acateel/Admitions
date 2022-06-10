package com.adminitions.admitions.tags;

import com.adminitions.entities.Faculty;
import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.TagSupport;


import java.io.IOException;

public class FacultyShowTag extends TagSupport {
    private String facultyId;
    private String name;
    private String budget;
    private String total;
    private static final String TD = "<td>";
    private static final String CLOSE_TD = "</td>";
    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        StringBuilder builder = new StringBuilder("");
        builder.append("<th scope=\"row\">").append(facultyId).append("</th>").append("\n");
        builder.append(TD).append(name).append(CLOSE_TD).append("\n");
        builder.append(TD).append(budget).append(CLOSE_TD).append("\n");
        builder.append(TD).append(total).append(CLOSE_TD).append("\n");

        try{
            out.print(builder.toString());
        }
        catch (IOException e){
            throw new JspException(e);
        }

        return SKIP_BODY;
    }

    public String getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(String facultyId) {
        this.facultyId = facultyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
