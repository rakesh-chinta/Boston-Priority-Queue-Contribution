recalculate: 
    { 
        struct task_struct *p; 
        struct list_head *listhead = &prio_queues[0]; 
        spin_unlock_irq(&runqueue_lock); 
        read_lock(&tasklist_lock); 
        for_each_task(p) 
            p->counter = (p->counter >> 1) + NICE_TO_TICKS(p->nice); 
        read_unlock(&tasklist_lock); 
        spin_lock_irq(&runqueue_lock);
        /* now we hold the lock again... 
         * all tasks should have been in the 0-bucket. 
         * after reassignment no task should be in the 0-bucket 
         * so we run through it and assign a new bucket for each task. 
         */

        for (tmp = listhead->next; tmp != listhead; ) { 
            /* we first advance because we are dequeueing */
            p = list_entry(tmp, struct task_struct, run_list); 
            tmp = tmp->next; 
            move_last_runqueue(p); 
        } 
    } 
    goto repeat_schedule;
    
