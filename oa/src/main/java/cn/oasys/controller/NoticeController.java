package cn.oasys.controller;

import cn.oasys.config.formValid.BindingResultVOUtil;
import cn.oasys.config.formValid.MapToList;
import cn.oasys.config.formValid.ResultEnum;
import cn.oasys.config.formValid.ResultVO;
import cn.oasys.pojo.Dept;
import cn.oasys.pojo.Position;
import cn.oasys.pojo.StatusList;
import cn.oasys.pojo.TypeList;
import cn.oasys.pojo.attch.Attachment;
import cn.oasys.pojo.notice.*;
import cn.oasys.pojo.user.User;
import cn.oasys.service.attch.AttachmentService;
import cn.oasys.service.dept.DeptService;
import cn.oasys.service.file.FileListService;
import cn.oasys.service.noice.CatalogService;
import cn.oasys.service.noice.NoteListService;
import cn.oasys.service.noice.ReceiverNoteService;
import cn.oasys.service.position.PositionService;
import cn.oasys.service.status.StatusListService;
import cn.oasys.service.type.TypeListService;
import cn.oasys.service.user.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.io.IOUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class NoticeController {
    @Autowired
    StatusListService statusListService;
    @Autowired
    TypeListService typeListService;
    @Autowired
    PositionService positionService;
    @Autowired
    DeptService deptService;
    @Autowired
    NoteListService noteListService;
    @Autowired
    CatalogService catalogService;
    @Autowired
    UserService userService;
    @Autowired
    AttachmentService attachmentService;
    @Autowired
    FileListService fileListService;
    @Autowired
    ReceiverNoteService receiverNoteService;

    /**
     * 笔记管理主页面
     * @param model
     * @param request
     * @param session
     * @param user
     * @param page
     * @param size
     * @param baseKey
     * @param type
     * @param status
     * @param time
     * @param icon
     * @return
     */
    @RequestMapping(value = "noteview", method = RequestMethod.GET)
    public String test(Model model, HttpServletRequest request, HttpSession session,
                       @SessionAttribute("user") User user,
                       @RequestParam(value="pageIndex",defaultValue="1")int page,
                       @RequestParam(value="size",defaultValue="10")int size,
                       @RequestParam(value = "baseKey", required = false) String baseKey,
                       @RequestParam(value = "type", required = false) String type,
                       @RequestParam(value = "status", required = false) String status,
                       @RequestParam(value = "time", required = false) String time,
                       @RequestParam(value = "icon", required = false) String icon) {
        List<Catalog> catalogList = catalogService.selectCatalogUser(user.getUserId());
        setSomething(baseKey, type, status, time, icon, model,null,null);
        PageInfo<NoteList> upage= noteListService.sortPage(page,size, baseKey, user.getUserId(),null,null,null, type, status, time);
        model.addAttribute("sort", "&userid="+user.getUserId());
        paging(model, upage);
        typestatus(request);
        model.addAttribute("url", "notewrite");
        model.addAttribute("calist", catalogList);
        return "note/noteview";
    }

    // 收藏查询
    @RequestMapping("collectfind")
    public String dsafdsf(Model model, HttpServletRequest request, @RequestParam("iscollect") String iscollected, @RequestParam("cata") Long cid,
                          HttpSession session,
                          @RequestParam(value="pageIndex",defaultValue="1")int page,
                          @RequestParam(value="size",defaultValue="10")int size,
                          @RequestParam(value="baseKey",required=false)String baseKey
            , @RequestParam(value = "type", required = false) String type,
                          @RequestParam(value = "status", required = false) String status,
                          @RequestParam(value = "time", required = false) String time,
                          @RequestParam(value = "icon", required = false) String icon
    ) {
        if(cid==-2)
            cid=null;
        Long userid = Long.valueOf(session.getAttribute("userId") + "");
        long collect = Long.valueOf(iscollected);

        System.out.println("收集"+collect);
        if (collect == 1) {
            setSomething(baseKey, type, status, time, icon, model,cid,null);
            PageInfo<NoteList> upage= noteListService.sortPage(page,size, null, userid, collect, cid, null, type, status, time);
            model.addAttribute("url", "collectfind");
            paging(model, upage);
            //获得数据之后就将cid重新设置
            if(cid==null)
                cid=-2l;
            model.addAttribute("sort", "&iscollect="+collect+"&cata="+cid);
            model.addAttribute("sort2", "&iscollect="+collect+"&cata="+cid);
            model.addAttribute("collect", 0);
        } else if (collect == 0) {
            setSomething(baseKey, type, status, time, icon, model,cid,null);
            PageInfo<NoteList> upage= noteListService.sortPage(page, size,null, userid, null, cid, null, type, status, time);
            model.addAttribute("url", "notewrite");
            paging(model, upage);
            model.addAttribute("sort", "&userid="+userid);
            model.addAttribute("sort2", "&userid="+userid);
            model.addAttribute("collect", 1);
        }

        typestatus(request);

        return "note/notewrite";
    }

    /**
     * 查看笔记
     * @param id
     * @param Request
     * @param response
     * @param session
     * @return
     * @throws IOException
     */
    @RequestMapping("noteinfo")
    public String test3(@Param("id") String id, HttpServletRequest Request, HttpServletResponse response,
                        HttpSession session) throws IOException {
        Attachment attachment = null;
        FileInputStream fis = null;
        OutputStream os = null;
        Long nid = Long.valueOf(id);
        NoteList note = noteListService.selectByPrimaryKey(nid);
        User user = userService.selectReviewed(note.getCreatemanId());
        if (note.getAttachId() != null) {
            Attachment att = attachmentService.selectByPrimaryKey(note.getAttachId());
            Request.setAttribute("att", att);
        }
        Request.setAttribute("note", note);
        Request.setAttribute("user", user);
        return "note/noteinfo";
    }

    /**
     * 修改
     * @param Request
     * @param session
     * @param model
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "noteedit")
    public String test4(HttpServletRequest Request, HttpSession session,Model model,
                        @SessionAttribute("user") User user,
                        @RequestParam(value="pageIndex",defaultValue="1")int page,
                        @RequestParam(value="size",defaultValue="10")int size) {
        //验证的重载
        if (!StringUtils.isEmpty(Request.getAttribute("errormess"))) {
            Request.setAttribute("errormess", Request.getAttribute("errormess"));
            Request.setAttribute("note", Request.getAttribute("note2"));
        } else if (!StringUtils.isEmpty(Request.getAttribute("success"))) {
            Request.setAttribute("success", Request.getAttribute("success"));
            Request.setAttribute("note", Request.getAttribute("note2"));
        }
        // 目录

        List<Catalog> catalogList = catalogService.selectCatalogUser(user.getUserId());
        //将根目录放在第一
        if(catalogList.size()==0)
            catalogList.add(catalogService.selectByPrimaryKey(33l));
        else
            catalogList.set(0, catalogService.selectByPrimaryKey(33l));

        System.out.println("目录"+catalogList);
        // 用户 就是联系人
        List<User> users = userService.selectAll();
        String nId = Request.getParameter("id");
        if (nId.contains("cata")) {
            //从目录编辑那里进来的
            String newnid = nId.substring(4, nId.length());
            long ca = Long.valueOf(newnid);
            Catalog cate = catalogService.selectByPrimaryKey(ca);
            Request.setAttribute("cata", cate);
            Request.setAttribute("note", null);
            Request.setAttribute("id", -3);
        } else {
            Long nid = Long.valueOf(nId);
            // 新建
            if (nid == -1) {
                Request.setAttribute("note", null);
                // 新建id
                Request.setAttribute("id", nid);
            }

            // 修改
            else if (nid > 0) {
                NoteList note = noteListService.selectByPrimaryKey(nid);
                long ca = Long.valueOf(note.getCatalogId());
                Catalog cate = catalogService.selectByPrimaryKey(ca);
                Request.setAttribute("cata", cate);
                Request.setAttribute("note", note);
                // 修改id
                Request.setAttribute("id", nid);
            }
            // Request.setAttribute("id", nid);
        }
        userget(page, size, model);

        Request.setAttribute("users", users);
        Request.setAttribute("calist", catalogList);
        typestatus(Request);
        return "note/noteedit";
    }

    /**
     * 修改保存
     * @param file
     * @param note2
     * @param br
     * @param request
     * @param session
     * @return
     * @throws IllegalStateException
     * @throws IOException
     */
    @RequestMapping(value = "notesave", method = RequestMethod.POST)
    public String testdfddf(@RequestParam("file") MultipartFile file,
                            @SessionAttribute("user") User user,
                            @Valid NoteList note2, BindingResult br,
                            HttpServletRequest request, HttpSession session) throws IllegalStateException, IOException {
        NoteList note = new NoteList();
        int i=0;
        Long attid = null;
        Set<User> userss = null;
        Long nid = Long.valueOf(request.getParameter("id"));
        // 接下来就是获取的数据
        String catalogname = request.getParameter("catalogname");
        String catalogName = catalogname.substring(1, catalogname.length());
        long catalogId = catalogService.findByCatalogName(catalogName);
        String typename = request.getParameter("type");
        long typeId = typeListService.findByTypeModelAndTypeName("aoa_note_list", typename).getTypeId();
        String statusName = request.getParameter("status");
        long statusId = statusListService.selectStatusId("aoa_note_list", statusName).getStatusId();

        // 这里返回ResultVO对象，如果校验通过，ResultEnum.SUCCESS.getCode()返回的值为200；否则就是没有通过；
        ResultVO res = BindingResultVOUtil.hasErrors(br);
        if (!ResultEnum.SUCCESS.getCode().equals(res.getCode())) {
            List<Object> list = new MapToList<>().mapToList(res.getData());
            request.setAttribute("errormess", list.get(0).toString());
        } else {
            // nid为-1就是新建或者是从某个目录新建
            if (nid == -1 || nid == -3) {
                // 判断文件是否为空
                if (!file.isEmpty()) {
                    System.out.println("进入");
                    Attachment  att = (Attachment) fileListService.saveFile(file, user, null, false);
                    attid = att.getAttachmentId();
                } else if (file.isEmpty())
                    attid = null;

                // 0l表示新建的时候默认为没有收藏
                System.out.println("title:"+note2.getTitle());
                note.setTitle(note2.getTitle());
                note.setContent(note2.getContent());
                note.setCatalogId(catalogId);
                note.setTypeId(typeId);
                note.setStatusId(statusId);
                note.setAttachId(attid);
                note.setCreateTime(new Date());
                note.setIsCollected(0l);
                // 判断是否共享
                if (request.getParameter("receiver") != null && (request.getParameter("receiver").trim().length() > 0)) {
                    userss = new HashSet<>();
                    String receivers = request.getParameter("receiver");
                    note.setReceiver(receivers);

                    String[] receiver = receivers.split(";");
                    // 先绑定自己再
                    userss.add(user);
                    // 再绑定其他人
                    for (String re : receiver) {
                        System.out.println(re);
                        User user2 = userService.certificationAudit(re);
                        if (user2 == null) {
                        } else
                            userss.add(user2);
                    }

                } else {
                    // 保存为该用户的笔记 绑定用户id
                    userss = new HashSet<>();
                    userss.add(user);
                }
            }
            // nid大于0就是修改某个对象
            if (nid > 0) {
                note = noteListService.selectByPrimaryKey(nid);
                if (note.getAttachId() == null) {
                    if (!file.isEmpty()) {
                        Attachment att = (Attachment) fileListService.saveFile(file, user, null, false);
                        attid = att.getAttachmentId();
                        note.setAttachId(attid);
                        noteListService.update(note);
                    }
                }
                if (note.getAttachId() != null)
                    fileListService.updateAtt(file, user, null, note.getAttachId());

                // 判断是否共享
                if (request.getParameter("receiver") != null && (request.getParameter("receiver").trim().length() > 0)) {
                    userss = new HashSet<>();
                    String receivers = request.getParameter("receiver");
                    note.setReceiver(receivers);

                    String[] receiver = receivers.split(";");
                    // 先绑定自己再
                    userss.add(user);
                    // 再绑定其他人
                    for (String re : receiver) {
                        System.out.println(re);
                        User user2 = userService.certificationAudit(re);
                        if (user2 == null) {
                        } else
                            userss.add(user2);
                    }

                } else {
                    // 保存为该用户的笔记 绑定用户id
                    userss = new HashSet<>();
                    userss.add(user);
                }
                i=noteListService.updateNote(catalogId, typeId, statusId, note2.getTitle(), note2.getContent(), nid);
            }
            request.setAttribute("success", "后台验证成功");
        }
        if (i==0) {
            // 设置创建人
            note.setCreatemanId(user.getUserId());
            note.setUserss(userss);
            noteListService.insert(note);
            Long noteId = noteListService.selectMaxId();
            ReceiverNote receiverNote = new ReceiverNote();
            receiverNote.setNoteId(noteId);
            receiverNote.setUserId(user.getUserId());
            receiverNoteService.insert(receiverNote);
            request.setAttribute("note2", note2);
        }
        return "forward:/noteedit";
    }

    // 下载文件
    @RequestMapping("down")
    public void dsaf(HttpServletResponse response, HttpServletRequest request,Attachment att) {
        if (StringUtils.isEmpty(request.getParameter("paid")) || request.getParameter("paid") == null
                || request.getParameter("paid").length() == 0) {
        } else {
            Long paid = Long.valueOf(request.getParameter("paid"));
            att = attachmentService.selectByPrimaryKey(paid);
        }
        if (StringUtils.isEmpty(request.getParameter("nid")) || request.getParameter("nid") == null
                || request.getParameter("nid").length() == 0) {
        } else {
            Long nid = Long.valueOf(request.getParameter("nid"));
            NoteList note = noteListService.selectByPrimaryKey(nid);
            att = attachmentService.selectByPrimaryKey(note.getAttachId());
        }
        File file = fileListService.get(att);
        System.out.println(file.getAbsolutePath());
        try {
            // 在浏览器里面显示
            response.setContentLength(att.getAttachmentSize().intValue());
            response.setContentType(att.getAttachmentType());
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + new String(att.getAttachmentName().getBytes("UTF-8"), "ISO8859-1"));
            ServletOutputStream sos = response.getOutputStream();
            byte[] data = new byte[att.getAttachmentSize().intValue()];
            IOUtils.readFully(new FileInputStream(file), data);
            IOUtils.write(data, sos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 显示表格所有
    @RequestMapping(value = "notewrite", method = RequestMethod.GET)
    public String test33(Model model, HttpServletRequest request,  HttpSession session,
                         @SessionAttribute("user") User user,
                         @RequestParam(value = "pageIndex", defaultValue = "1") int page,
                         @RequestParam(value="size",defaultValue="10")int size,
                         @RequestParam(value = "baseKey", required = false) String baseKey,
                         @RequestParam(value = "type", required = false) String type,
                         @RequestParam(value = "status", required = false) String status,
                         @RequestParam(value = "time", required = false) String time,
                         @RequestParam(value = "icon", required = false) String icon) {

        setSomething(baseKey, type, status, time, icon, model,null,null);
        PageInfo<NoteList> upage=noteListService.sortPage(page,size, baseKey, user.getUserId(),null,null,null, type, status, time);
        typestatus(request);
        if(baseKey!=null){
            //如果有搜索关键字那么就记住它
            request.setAttribute("sort", "&baseKey="+baseKey);
        }
        //没有就默认查找所有
        else
            request.setAttribute("sort", "&userid="+user.getUserId());
        paging(model, upage);
        model.addAttribute("url", "notewrite");
        return "note/notewrite";
    }


    // 笔记删除
    @RequestMapping("notedelete")
    public String testrw(Model model, HttpServletRequest request, HttpSession session) {
        long realuserId = Long.valueOf(session.getAttribute("userId") + "");
        String nid = request.getParameter("nid");
        long noteid = Long.valueOf(nid);
        ReceiverNote u = receiverNoteService.findUserId(noteid, realuserId);
        if (u != null) {
            notedelete(realuserId, noteid);
            return "redirect:/noteview";
        } else {
            System.out.println("权限不匹配，不能删除");
            return "redirect:/notlimit";

        }

    }

    // 笔记批量删除
    @RequestMapping("notesomedelete")
    public String dsafds(HttpServletRequest request, HttpSession session) {
        long realuserId = Long.valueOf(session.getAttribute("userId") + "");
        String sum = request.getParameter("sum");
        String[] strings = sum.split(";");
        for (String s : strings) {
            long noteids = Long.valueOf(s);
            notedelete(realuserId, noteids);
        }
        return "redirect:/noteview";
    }

    // 查找类型
    @RequestMapping("notetype")
    public String test43(Model model, HttpServletRequest request,
                         @RequestParam("typeid") Long tid,
                         @RequestParam("id") Long cid, HttpSession session,
                         @SessionAttribute("user") User user,
                         @RequestParam(value="pageIndex",defaultValue="1")int page,
                         @RequestParam(value="size",defaultValue="0")int size,
                         @RequestParam(value = "baseKey", required = false) String baseKey,
                         @RequestParam(value = "type", required = false) String type,
                         @RequestParam(value = "status", required = false) String status,
                         @RequestParam(value = "time", required = false) String time,
                         @RequestParam(value = "icon", required = false) String icon
    ) {
        System.out.println(tid);
        if(cid==-2)
            cid=null;
        System.out.println("目录"+cid);
        setSomething(baseKey, type, status, time, icon, model,cid,tid);
        PageInfo<NoteList> upage=noteListService.sortPage(page,size, baseKey, user.getUserId(), null, cid, tid, type, status, time);
        System.out.println(upage.getList());
        //获得数据之后就将cid重新设置
        if(cid==null)
            cid=-2l;
        request.setAttribute("sort2", "&id="+cid+"&typeid="+tid);
        paging(model, upage);
        model.addAttribute("url", "notetype");
        typestatus(request);
        return "note/notewrite";
    }

    //查找目录
    @RequestMapping("notecata")
    public String sadf(Model model, HttpServletRequest request,  HttpSession session,
                       @RequestParam("id")String cid,
                       @SessionAttribute("user") User user,
                       @RequestParam(value="pageIndex",defaultValue="1")int page,
                       @RequestParam(value="size",defaultValue="10")int size,
                       @RequestParam(value = "baseKey", required = false) String baseKey,
                       @RequestParam(value = "type", required = false) String type,
                       @RequestParam(value = "status", required = false) String status,
                       @RequestParam(value = "time", required = false) String time,
                       @RequestParam(value = "icon", required = false) String icon
    ){
        Long userid = Long.parseLong(session.getAttribute("userId") + "");
        model.addAttribute("catalog", "&id="+cid);
        //不为-2就是按照目录查找
        if (!request.getParameter("id").equals("-2")) {
            Long id = Long.valueOf(cid);
            setSomething(baseKey, type, status, time, icon, model,id,null);
            PageInfo<NoteList> upage=noteListService.sortPage(page,size, baseKey, userid, null, id, null, type, status, time);
            request.setAttribute("sort2", "&id="+cid);
            paging(model, upage);
            model.addAttribute("url", "notecata");
            ////为-2就是按照最近查找
        }
        typestatus(request);
        return "note/notewrite";
    }

    // post请求 添加类型
    @RequestMapping(value = "noteview", method = RequestMethod.POST)
    public String test3332(HttpServletRequest request,
                           @SessionAttribute("user") User user,
                           @Param("title") String title, HttpSession session) {
        int flag = 0;
        String catalogName = request.getParameter("name");
        if (catalogName != null) {
            List<String> catanamelist = catalogService.selectCatalogName(user.getUserId());
            for (String caname : catanamelist) {
                if (caname.contains("(") && caname.contains(")"))
                    caname = caname.substring(0, caname.indexOf("("));
                if (caname.equals(catalogName)) {
                    flag++;
                }
            }
            if (flag == 0)
                catalogService.insertCatalog(catalogName, user.getUserId());
            if (flag > 0)
                catalogService.insertCatalog(catalogName + "(" + flag + ")", user.getUserId());
        }
        return "redirect:/noteview";
    }

    private void notedelete(long realUserId, long noteId) {
        //删除共享笔记就是只删除中间表noteid对应的那个userid
        NoteList note =noteListService.selectByPrimaryKey(noteId);
        if(note.getTypeId()==7)
        {
            receiverNoteService.deleteById(receiverNoteService.findUserId(noteId, realUserId).getId());
        }
        //如果笔记的类型不是共享类型的就直接删除
        else
            noteListService.delete(noteId);
    }

    public void userget(int page,int size,Model model){
        PageHelper.startPage(page,size);
        //查看用户并分页
        PageInfo<User> pageInfo=new PageInfo<>(userService.selectAllUser());
        List<User> userList=pageInfo.getList();
        // 查询部门表
        Iterable<Dept> deptList = deptService.selectAll();
        // 查职位表
        Iterable<Position> posList = positionService.selectAll();
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("emplist", userList);
        model.addAttribute("deptlist", deptList);
        model.addAttribute("poslist", posList);
        model.addAttribute("url", "namereceive");
    }

    private void typestatus(HttpServletRequest request) {
        List<TypeList> type = typeListService.findByTypeModel("aoa_note_list");
        List<StatusList> status = statusListService.findByStatusModel("aoa_note_list");
        request.setAttribute("typelist", type);
        request.setAttribute("statuslist", status);
    }

    //分页
    private void paging(Model model, PageInfo<NoteList> pageInfo) {
        model.addAttribute("nlist", pageInfo.getList());
        model.addAttribute("pageInfo", pageInfo);
//		model.addAttribute("url", "notewrite");
    }

    public void setSomething(String baseKey, String type, String status, String time, String icon, Model model,Long cataid,Long typeid) {
        if(!StringUtils.isEmpty(icon)){
            model.addAttribute("icon", icon);
            if(!StringUtils.isEmpty(type)){
                model.addAttribute("type", type);
                setthree("type",type, icon, model, cataid, typeid);
            }
            if(!StringUtils.isEmpty(status)){
                model.addAttribute("status", status);
                setthree("status",status, icon, model, cataid, typeid);
            }
            if(!StringUtils.isEmpty(time)){
                model.addAttribute("time", time);
                setthree("time",time, icon, model, cataid, typeid);
            }

        }
        if(StringUtils.isEmpty(icon)){
            //目录类型查找
            if(!StringUtils.isEmpty(cataid)&&!StringUtils.isEmpty(typeid))
                model.addAttribute("sort", "&id="+cataid+"&typeid="+typeid);
            //目录单纯查找
            if(!StringUtils.isEmpty(cataid)&&StringUtils.isEmpty(typeid))
                model.addAttribute("sort", "&id="+cataid);
            //单纯类型查找
            if(StringUtils.isEmpty(cataid)&&!StringUtils.isEmpty(typeid))
                model.addAttribute("sort", "&typeid="+typeid);
        }

        if(!StringUtils.isEmpty(baseKey)&&StringUtils.isEmpty(cataid)){
            model.addAttribute("sort", "&baseKey="+baseKey);
        }
        if(!StringUtils.isEmpty(baseKey)&&!StringUtils.isEmpty(cataid)){
            model.addAttribute("sort", "&baseKey="+baseKey+"&id="+cataid);
        }

    }

    private void setthree(String x,String name, String icon, Model model, Long cataid, Long typeid) {
        //单纯根据目录
        if(!StringUtils.isEmpty(cataid)&&StringUtils.isEmpty(typeid))
            model.addAttribute("sort", "&"+x+"="+name+"&icon="+icon+"&id="+cataid);
        //单纯的根据类型
        if(StringUtils.isEmpty(cataid)&&!StringUtils.isEmpty(typeid))
            model.addAttribute("sort", "&"+x+"="+name+"&icon="+icon+"&typeid="+typeid);
        //根据目录和类型
        if(!StringUtils.isEmpty(cataid)&&!StringUtils.isEmpty(typeid))
            model.addAttribute("sort", "&"+x+"="+name+"&icon="+icon+"&id="+cataid+"&typeid="+typeid);
        else if(StringUtils.isEmpty(cataid)&&StringUtils.isEmpty(typeid))
            model.addAttribute("sort", "&"+x+"="+name+"&icon="+icon);
    }
}
