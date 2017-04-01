search_range:
while (qid >= botqid) { 
    int found_local_task = 0; 
    list_for_each(tmp, &prio_queues[qid]) { 
        p = list_entry(tmp, struct task_struct, run_list); 
        if (can_schedule(p, this_cpu)) { 
            int weight; 
            weight = goodness(p,this_cpu,prev->active_mm); 
            if (weight == 0) { 
                 /* should not be in this queue, but 
                  * error in timer.c where a running 
                  * task is not on the runqueue 
                  */ 
                tmp = tmp->prev; 
                list_del(&p->run_list); 
                list_add_tail(&p->run_list,&prio_queues[0]); 
                continue; 
            } 
            if (p->processor == this_cpu) 
                found_local_task = 1; 
            if (weight > c) 
                c = weight, next = p; 
        } 
    } 
    if (found_local_task) 
        goto found_local_task_cont; 
    qid--; 
}
