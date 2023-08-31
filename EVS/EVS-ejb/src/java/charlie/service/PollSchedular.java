package charlie.service;

import charlie.dao.PollAccess;
import charlie.entity.PollEntity;
import charlie.entity.PollStateEnum;
import charlie.utils.DateUtils;
import java.util.Date;
import java.util.logging.Level;
import javax.ejb.Singleton;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Schedule;

@Singleton
public class PollSchedular {

    private static final Logger LOG = Logger.getLogger(PollSchedular.class.getName());

    @EJB
    private PollAccess pollDao;

    @Schedule(hour = "*", minute = "*/2", second = "0", persistent = false)
    public void findAndChangePollStatusToVotingAfterVotingPeriodStarts() {
        Date toDate = new Date();
        Date fiveMinutesBack = DateUtils.getDateMinusFiveMinutes();
        LOG.log(Level.INFO, "getting started polls from: " + fiveMinutesBack.toString() + " to: " + toDate.toString());
        var polls = pollDao.getPollsInStartedStateInDateRange(fiveMinutesBack, toDate);
        LOG.log(Level.INFO, "got polls as: " + polls);
        
        if(polls == null || polls.isEmpty())
            return;
        
        
        for(PollEntity poll : polls) {
            poll.setState(PollStateEnum.VOTING);
            pollDao.edit(poll);
        }
    }
    
    @Schedule(hour = "*", minute = "*/2", second = "0", persistent = false)
    public void findAndChangePollStatusToFinishedAfterVotingPeriodEnds() {
        Date toDate = new Date();
        Date fiveMinutesBack = DateUtils.getDateMinusFiveMinutes();
        LOG.log(Level.INFO, "getting voting polls from: " + fiveMinutesBack.toString() + " to: " + toDate.toString());
        var polls = pollDao.getPollsInVotingStateInDateRange(fiveMinutesBack, toDate);
        LOG.log(Level.INFO, "got polls as: " + polls);
        
        if(polls == null || polls.isEmpty())
            return;
        
        
        for(PollEntity poll : polls) {
            poll.setState(PollStateEnum.FINISHED);
            pollDao.edit(poll);
        }
    }
}
