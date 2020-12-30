import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class CatTree implements Iterable<CatInfo>{
    public CatNode root;
    
    public CatTree(CatInfo c) {
        this.root = new CatNode(c);
    }
    
    private CatTree(CatNode c) {
        this.root = c;
    }    
    
    public void addCat(CatInfo c)
    {
        this.root = root.addCat(new CatNode(c));
    }
    
    public void removeCat(CatInfo c)
    {
        this.root = root.removeCat(c);
    }
    
    public int mostSenior()
    {
        return root.mostSenior();
    }
    
    public int fluffiest() {
        return root.fluffiest();
    }
    
    public CatInfo fluffiestFromMonth(int month) {
        return root.fluffiestFromMonth(month);
    }
    
    public int hiredFromMonths(int monthMin, int monthMax) {
        return root.hiredFromMonths(monthMin, monthMax);
    }
    
    public int[] costPlanning(int nbMonths) {
        return root.costPlanning(nbMonths);
    }
    
    public Iterator<CatInfo> iterator()
    {
        return new CatTreeIterator();
    }
    
    class CatNode {        
        CatInfo data;
        CatNode senior;
        CatNode same;
        CatNode junior;
        
        public CatNode(CatInfo data) {
            this.data = data;
            this.senior = null;
            this.same = null;
            this.junior = null;
        }
        
        public String toString() {
            String result = this.data.toString() + "\n";
            if (this.senior != null) {
                result += "more senior " + this.data.toString() + " :\n";
                result += this.senior.toString();
            }
            if (this.same != null) {
                result += "same seniority " + this.data.toString() + " :\n";
                result += this.same.toString();
            }
            if (this.junior != null) {
                result += "more junior " + this.data.toString() + " :\n";
                result += this.junior.toString();
            }
            return result;
        }
        
        //	Add cat:
        public CatNode addCat(CatNode c) {
        	//	If tree is empty, set the node to be the root.
        	if(root.equals(null)) {
        		root = c;
        	}
        	else if(this.equals(null)) {
        		
        	}
            //	The month hired of c < root. So c is more senior.
        	else if(c.data.monthHired < this.data.monthHired) {
        		if(this.senior==null) {
        			this.senior = c;
        		}
        		else {
        			this.senior.addCat(c);			//	Calling method on the senior of the root
        		}
        	}
        	
        	//	The month hired of c > root. So c is more junior.
        	else if(c.data.monthHired > this.data.monthHired) {
        		if(this.junior == null) {
        			this.junior = c;
        		}
        		else {
        			this.junior.addCat(c);			//	Calling method on the junior of the root
        		}
        	}
        	
        	//	The month hired of c == CatNode. Same seniority.
        	else if (c.data.monthHired == this.data.monthHired) {
        		//	Comparing thickness.
        		//	If the thickness of c > root. Set c to be the root.
        		if(c.data.furThickness > this.data.furThickness) {
        			//	In the code below you don't need to change the senior & junior.
        			//	We put the node bellow the root & switch the data. 
        			CatNode tempSame = this.same;
        			CatInfo tempInfo = this.data;
        			this.same = c;
        			c.same = tempSame;
        			this.data = c.data;
        			c.data = tempInfo;
        			
        			//	In this code we are putting the node in the tree by changing the senior and junior.
        			/*c.senior = this.senior;
        			c.junior = this.junior;
        			c.same = this;
        			this.senior = null;
        			this.junior = null;
        			root = c;*/
        		}
        		//	If the thickness of c = root. Set c to come after the root.
        		else if(c.data.furThickness == this.data.furThickness) {
        			CatNode temp = this.same;
        			this.same = c;
        			c.same = temp;
        		}
        		//	If the thickness of c < root.
        		//	We iterate thought the rest of the list and find its correct place.
        		else {
        			CatNode current = this;
        			while(true) {
        				//	If we haven't reached the last node
        				if(current.same != null) {
        					//	If c is less thick than the current node.
        					//	Update the current, continue iterating.        					
        					if(current.same.data.furThickness > c.data.furThickness) {
        						current = current.same;
        					}
        					//	If c is more thick than the current node. Add to before.
        					else if(current.same.data.furThickness < c.data.furThickness) {
        						CatNode temp = current.same;
        						c.same = temp;
        						current.same = c;
        						break;
        					}
        					else if(current.same.data.furThickness == c.data.furThickness) {
        						CatNode temp = current.same;
        	        			current.same = c;
        	        			c.same = temp;
        	        			break;
        					}
        				}
        				// If we have reached the last node
        				else {
        					current.same = c;
        					break;
        				}
        			}
        		}        		
        	}        	
            return root;
        }
        
        
        //	Remove cat:
        private CatNode remove(CatNode current, CatInfo key) {
        	if(current == null) {
        		return null;        		
        	}
        	else if(key.monthHired < current.data.monthHired) {
        		current.senior = remove(current.senior, key);
        	}
        	else if(key.monthHired > current.data.monthHired) {
        		current.junior = remove(current.junior, key);
        	}
        	else if(key.monthHired == current.data.monthHired) {
        		current.same = remove(current.same, key);
        	}
        	else if(current.data.equals(key)) {        		
        		if(current.same != null) {
        			CatNode temp = current.same.same;
        			current.data = current.same.data;
        			current.same = temp;        			
        		}
        		else if(current.same == null && current.senior != null) {
        			CatNode tempJunior = current.senior;
        			while(tempJunior.junior != null) {
        				tempJunior = tempJunior.junior;
        			}
        			CatNode tempSenior = current.senior.senior;    			
        			CatNode tempSame = current.senior.same;
        			CatNode tempJuniorExt = current.junior;
        			current.data = current.senior.data;
        			current.same = tempSame;
        			current.senior = tempSenior;
        			
        			if(tempJunior.data.equals(current.data)) {
        				current.junior = tempJuniorExt;
        			}
        			else {
        				tempJunior.junior = tempJuniorExt;
        				current.junior = tempJunior;
        			}
        		}
        		else if(current.same == null && current.senior == null && current.junior != null) {
        			CatNode temp = current.junior;
        			CatNode tempJunior = current.junior.junior;
        			CatNode tempSame = current.junior.same;
        			CatNode tempSenior = current.junior.senior;
        			
        			current.data = current.junior.data;
        			current.junior = tempJunior;
        			current.same = tempSame;
        			current.senior = tempSenior;
        		}
        	}        	
        	return current;
        }
        /*
         
        	//TODO what to return when we only have the root?
    		else if(node.same == null && node.senior == null && node.junior == null) {
    			if(node.equals(root)) {
    				root = null;
    			}
    			node = null;
    		}
         */
        
        
        private CatNode findNode(CatNode current,CatInfo c) {
        	if(current == null) return null;
        	else if (current.data.equals(c)) return current;
        	else if (current.data.monthHired > c.monthHired) return findNode(current.senior, c);
        	else if (current.data.monthHired < c.monthHired) return findNode(current.junior, c);
        	else if (current.data.monthHired == c.monthHired) return findNode(current.same, c);
        	return null;
        }
        
        private CatNode findPrevNode(CatNode c, CatNode key) {        	
        	if(c != null && c.senior != null && c.senior.equals(key)) return c;
        	else if(c != null && c.junior != null && c.junior.equals(key)) return c;
        	else if (c != null && c.same != null && c.same.equals(key)) return c;
        	
        	if(c != null) {
        		if (c.data.monthHired > key.data.monthHired) return findPrevNode(c.senior, key);
            	else if (c.data.monthHired < key.data.monthHired) return findPrevNode(c.junior, key);
            	else if(c.data.monthHired == key.data.monthHired) return findPrevNode(c.same, key);
        	}        	
        	return null;
        }
        
        private void delete(CatNode node, CatInfo c) {
        	if(node.same != null) {
        		CatNode temp = node.same.same;
    			node.data = node.same.data;
    			node.same = temp;
        	}
        	else if(node.same == null && node.senior != null) {
    			CatNode tempJunior = node.senior;
    			while(tempJunior.junior != null) {
    				tempJunior = tempJunior.junior;
    			}
    			CatNode tempSenior = node.senior.senior;    			
    			CatNode tempSame = node.senior.same;
    			CatNode tempJuniorExt = node.junior;
    			node.data = node.senior.data;
    			node.same = tempSame;
    			node.senior = tempSenior;
    			if(tempJunior.data.equals(node.data)) {
    				node.junior = tempJuniorExt;
    			}
    			else {
    				tempJunior.junior = tempJuniorExt;
    				node.junior = tempJunior;
    			}
    		}
    		else if(node.same == null && node.senior == null && node.junior != null) {
    			CatNode temp = node.junior;
    			CatNode tempJunior = node.junior.junior;
    			CatNode tempSame = node.junior.same;
    			CatNode tempSenior = node.junior.senior;
    			
    			node.data = node.junior.data;
    			node.junior = tempJunior;
    			node.same = tempSame;
    			node.senior = tempSenior;
    		}
        	/*
        	//TODO what to return when we only have the root?
    		else if(node.same == null && node.senior == null && node.junior == null) {
    			if(node.equals(root)) {
    				root = null;
    			}
    			node = null;
    		}*/
        }
        
        
        public CatNode removeCat(CatInfo c) {
        	// Now only returning the root it was called on
        	CatNode node = findNode(this, c);
        	//System.out.println("fonund node: "+ node);
        	if (node==null) return this;
        	else if(node.senior == null && node.junior == null && node.same == null) {
        		CatNode prev = findPrevNode(root, node);
        		//System.out.println("Node is leaf, prev: "+ prev);
        		//System.out.println("node we are looking for: "+ node);
        		
        		
        		if(prev.senior!=null && prev.senior.equals(node)) {
        			//System.out.println("node it senior to prev.");
        			prev.senior = null;  			
        		}
        		else if (prev.junior!=null && prev.junior.equals(node)) {
        			//System.out.println("node it junior to prev.");
        			prev.junior = null;
        		}
        		else if	(prev.same!=null &&prev.same.equals(node)) {
        			//System.out.println("node it same to prev.");
        			prev.same = null;
        		}
        		/*
        		else if (prev!=null && prev.equals(root)) {
        			System.out.println("node is prev.");
        			prev = null;
        			root = null;
        		}*/
        		return this;
        	}
        	else {
        		delete(node, c);
        		return this;
        	}
        }
        
        
        //	Most Senior:
        public int mostSenior() {
        	CatNode current = this;
        	if(current == null) return 0;
        	else {        		
                while(current.senior != null) {
                	current = current.senior;
                }            
                return current.data.monthHired;
        	}        	
        }
        
        //	Fluffiest:
        int max;
        public int fluffiest() {       
        	max = 0;
        	preSearchFluffiest(this, 0);
        	return max;
        }
        private void preSearchFluffiest(CatNode c, int i) {
        	max = i;      	
        	if(c != null) {
        		if(c.data.furThickness > max) max = c.data.furThickness;
        		 preSearchFluffiest(c.senior, max);
        		 preSearchFluffiest(c.same, max);        		 
        		 preSearchFluffiest(c.junior, max);
        	}
        }        
        
        //	Hired from months
        int n;        
        public int hiredFromMonths(int monthMin, int monthMax) {
        	n = 0;
        	if(monthMin > monthMax) return 0;
            else {
            	preSearchHired(monthMin, monthMax, this, 0);
            	return n;
            }
        }
        private void preSearchHired(int min, int max, CatNode current, int i) {
        	n = i;
        	if(current != null) {
        	/*
       		preSearchHired(min, max, current.senior, n);
       		preSearchHired(min, max, current.same, n);
   			preSearchHired(min, max, current.junior, n);
   			*/
       		 if (current.data.monthHired > max) {
       			preSearchHired(min, max, current.senior, n);
       		 }
       		 else if(current.data.monthHired < min) {
       			preSearchHired(min, max, current.junior, n);
       		 }
       		 else if (current.data.monthHired >= min && current.data.monthHired <= max) {
       			if(current.data.monthHired >= min && current.data.monthHired <= max)n++;
       			preSearchHired(min, max, current.senior, n);
       			preSearchHired(min, max, current.same, n);       			
               	preSearchHired(min, max, current.junior, n);
       		 }       		         		 
        	}
        }
        
        //	Fluffiest from month
        public CatInfo fluffiestFromMonth(int month) {
        	CatInfo node = null;
        	node = preSearchFluffiestMonth(this, month);
        	return node;
        }        
        private CatInfo preSearchFluffiestMonth(CatNode c, int month) {
            if(c != null) {
            	if(c.data.monthHired == month){
            		return c.data;
                }        
            	else if(c.data.monthHired > month) {
                	return preSearchFluffiestMonth(c.senior, month);
                }    
                else if(c.data.monthHired < month) {
                	return preSearchFluffiestMonth(c.junior, month);
                }  
            }
            	return null;
        }
        
        
        // Cost Planning
        int[] arrCostPlanning;
        public int[] costPlanning(int nbMonths) {
        	arrCostPlanning = new int[nbMonths];
        	costPlanningSearch(this);            
            return arrCostPlanning;
        }
        private void costPlanningSearch(CatNode c) {
        	if(c != null) {
        		costPlanningSearch(c.senior);
        		costPlanningSearch(c.same);
        		{
                    int currentCost = c.data.expectedGroomingCost;
                    int currentIndex = c.data.nextGroomingAppointment-243;
                    int oldCost = arrCostPlanning[currentIndex];
                    
                    arrCostPlanning[currentIndex] = oldCost + currentCost;
        		} 
        		costPlanningSearch(c.junior);
        	}
        }
    }
    
    // Iterator
    private class CatTreeIterator implements Iterator<CatInfo> {        
    	ArrayList<CatNode> arr;
    	
        public CatTreeIterator() {
        	this.arr = new ArrayList<CatNode>();
            this.search(root);
        }
        
        public CatInfo next(){
        	if(arr.size()-1 < 0) throw new NoSuchElementException();
        	else {
        		CatInfo rtn = this.arr.get(arr.size()-1).data;
            	arr.remove(arr.size()-1);
            	return rtn;
        	}
        }
        
        public boolean hasNext() {
        	return this.arr.size()-1 != -1;
        }        
        private void search(CatNode c) {
        	// We are storing the first element (the most senior with most thickness) at the end of the stack.
        	// So the most junior and the thickest will be at 0 and the most senior and least thick at last.
        	// So we will empty the Array List from the end.
        	if (c != null) {
        		search(c.junior);
        		arr.add(c);
        		//System.out.println("added (to the end): "+c);
        		search(c.same);
        		search(c.senior);        		
        	}        	
        }
    }
}