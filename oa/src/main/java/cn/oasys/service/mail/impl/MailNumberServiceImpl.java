package cn.oasys.service.mail.impl;

import cn.oasys.dao.mail.MailNumberMapper;
import cn.oasys.pojo.mail.MailNumber;
import cn.oasys.pojo.user.User;
import cn.oasys.service.BaseServiceImpl;
import cn.oasys.service.mail.MailNumberService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("mailNumberService")
public class MailNumberServiceImpl extends BaseServiceImpl<MailNumber> implements MailNumberService {
    @Autowired
    MailNumberMapper mailNumberMapper;


    /**
     * 账号
     * @param page
     * @param size
     * @param tu
     * @param val
     * @return
     */
    @Override
    public PageInfo<MailNumber> index(int page, int size, User tu, String val, Model model){
        PageHelper.startPage(page,size);
        PageInfo<MailNumber> account=null;
        List<Sort.Order> orders = new ArrayList<>();
        if(StringUtil.isEmpty(val)){
            orders.addAll(Arrays.asList(new Sort.Order(Sort.Direction.ASC, "status"), new Sort.Order(Sort.Direction.DESC, "mailCreateTime")));
            Sort sort = new Sort(orders);
//            pa=new PageRequest(page, size, sort);
            account=new PageInfo<MailNumber>(mailNumberMapper.findByMailUserId(tu.getUserId()));
        }else if (("类型").equals(val)) {
            account=new PageInfo<MailNumber>(mailNumberMapper.findByMailUserIdOrderByMailType(tu.getUserId()));
            model.addAttribute("sort", "&val="+val);
        }else if(("状态").equals(val)){
            account=new PageInfo<MailNumber>(mailNumberMapper.findByMailUserIdOrderByStatus(tu.getUserId()));
            model.addAttribute("sort", "&val="+val);
        }else if(("创建时间").equals(val)){
            account=new PageInfo<MailNumber>(mailNumberMapper.findByMailUserIdOrderByMailCreateTimeDesc(tu.getUserId()));
            model.addAttribute("sort", "&val="+val);
        }else{
            //名字的模糊查询
            account = new PageInfo<MailNumber>(mailNumberMapper.findByMailUserNameLikeAndMailUserId(val,tu.getUserId()));
            model.addAttribute("sort", "&val="+val);
        }
        return account;
    }

    @Override
    public int insertMailNumber(MailNumber mail) {
        return mailNumberMapper.insertMailNumber(mail);
    }

    @Override
    public MailNumber selectAccountInformation(Long accountId) {
        return mailNumberMapper.selectAccountInformation(accountId);
    }

    @Override
    public List<MailNumber> findByStatusAndMailUserId(Long status, Long userId) {
        return mailNumberMapper.findByStatusAndMailUserId(status,userId);
    }

    @Override
    public MailNumber selectMailNumber(Long inmail) {
        return mailNumberMapper.selectMailNumber(inmail);
    }

    /**
     * 校验中文
     * @param str
     * @return
     */
    @Override
    public  boolean isContainChinese(String str) {

        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }


}
