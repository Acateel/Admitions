package com.adminitions.admitions.tags;

import com.adminitions.entities.Faculty;
import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.TagSupport;


import java.io.IOException;

public class FacultyShowTag extends TagSupport {
    private Faculty faculty;
    private static final String TD = "<td>";
    private static final String CLOSE_TD = "</td>";
    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        StringBuilder builder = new StringBuilder("");
        builder.append("<th scope=\"row\">").append(faculty.getId()).append("</th>").append("\n");
        builder.append(TD).append(faculty.getName()).append(CLOSE_TD).append("\n");
        builder.append(TD).append(faculty.getTotalSeats()).append(CLOSE_TD).append("\n");
        builder.append(TD).append(faculty.getBudgetSeats()).append(CLOSE_TD).append("\n");

        try{
            out.print(builder.toString());
        }
        catch (IOException e){
            throw new JspException(e);
        }

        return SKIP_BODY;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }
}
