package servlets;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ruzzleSolver.RuzzleHelper;


@SuppressWarnings("serial")
public class RuzzlesolverServlet extends HttpServlet {
  public void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {
    resp.setContentType("text/html");
    resp.getWriter().println("<style>body {font-family:Courier;}</style>");
    String gridString = (String) req.getParameter("grid");
    String gridBonusString = (String) req.getParameter("gridBonus");
    RuzzleHelper.buildGrid(gridString);
    RuzzleHelper.buildGridBonus(gridBonusString);
    RuzzleHelper.solveGrid();
    String wordsFound = RuzzleHelper.getWordsFound().toString().
        replaceAll("\n", "<br/>");
    wordsFound = wordsFound.replaceAll(" ", "&nbsp;");
    resp.getWriter().println(wordsFound);
  }
}
