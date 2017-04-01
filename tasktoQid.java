static int  task_to_qid(struct task_struct *p) 
{ 
    int qid;

    /* 
     * Be sure to give sufficiently high boost to realtime tasks 
     */ 
    if ((p->policy & ~SCHED_YIELD) != SCHED_OTHER) { 
        qid = REALTIME_QID; 
        goto out; 
    }

    /* 
     * na_goodness is based on the number of ticks left. 
     * Don't do any other calculations if the time slice is 
     * over.. 
     */ 
    qid = p->counter; 
    if (!qid) 
        goto out;
    qid += 20 - p->nice;

    /* Now translate the na_goodness value to a queue index qid
     *  00       =>   0         ratio
     *  01..16   =>   1..8      2:1 
     *  17..32   =>   9..24     1:1
     *  33..63   =>   25..31    4:1 
     */

if (qid < 17) { 
    qid = (qid == 0) ? 0 : ((qid > 0) ? ((qid+1)>>1) : 1); 
    goto out; 
} 
if (qid > 32) { 
    qid = 24 + ((qid & 31) >> 2); 
    goto out; 
} 
qid = qid - 8;

out: 
    return qid; 
} 
