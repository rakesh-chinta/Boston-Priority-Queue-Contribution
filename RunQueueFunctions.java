static inline void add_to_runqueue(struct task_struct * p) 
{ 
        int qid = task_to_qid(p); 
        list_add(&p->run_list, &prio_queues[qid]); 
        if (qid > topqid) topqid = qid; 
        nr_running++; 
}
static inline void move_last_runqueue(struct task_struct * p) 
{ 
        int qid = task_to_qid(p); 
        list_del(&p->run_list); 
        list_add_tail(&p->run_list, &prio_queues[qid]); 
        if (qid > topqid) topqid = qid; 
}

static inline void move_first_runqueue(struct task_struct * p) 
{ 
        int qid = task_to_qid(p); 
        list_del(&p->run_list); 
        list_add(&p->run_list, &prio_queues[qid]); 
        if (qid > topqid) topqid = qid; 
}

