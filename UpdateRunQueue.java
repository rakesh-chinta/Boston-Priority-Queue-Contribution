void update_runqueue(struct task_struct * p) 
{ 
        /* this is called from three locations 
         * timer.c   update_timer 
         * fork.c 
         * exit.c 
         */
        unsigned long flags; 
        int qid;

        /* there seems to be a bug every so often that a process 
         * that is running does not seem on the runqueue. 
         * this problem has not been identified yet.. we just keep the 
         * task where it is for this rare scenario. 
         * Also idle processes shouldn't be updated.. 
         * The scheduler will catch this problem anyway. 
         */ 
        if (!task_on_runqueue(p)) 
            return; 
        spin_lock_irqsave(&runqueue_lock, flags); 
        qid = task_to_qid(p); 
        list_del(&p->run_list); 
        list_add(&p->run_list, &prio_queues[qid]); 
        if (qid > topqid) topqid = qid; 
        spin_unlock_irqrestore(&runqueue_lock, flags); 
}
