import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import { Button } from '@/components/ui/button';
import {useNavigate} from 'react-router-dom';
import { useState } from 'react';


const EventTicketingSystem = () => {
  const navigate=useNavigate();
  const [totalTickets, settotalTickets] = useState('');
  const [releaseRate, setreleaseRate] = useState('');
  const [retrievalRate, SetretrievalRate] = useState('');
  const [maxCap, setmaxCap] = useState('');
  const [noVendors, setnoVendors] = useState('');
  const [noCustomers,setnoCustomers] = useState('');

  const handleConfigureClick=()=>{
    const configure={totalTickets,releaseRate,retrievalRate,maxCap,noVendors,noCustomers}
    console.log(configure)
    fetch("http://localhost:8080/configure/add",{
      method:"POST",
      headers:{"Content-Type":"application/json"},
      body:JSON.stringify(configure)
    })

    navigate('/Admin');
  }
  return (
    <Card className="w-full max-w-md mx-auto mt-[50px] mb-[50px]">
      <CardHeader>
        <CardTitle className="text-center text-xl font-bold align-middle">
          CONFIGURE THE PARAMETERS
        </CardTitle>
      </CardHeader>
      <CardContent>
        <div className="space-y-4">
          <div className="space-y-2">
            <Label htmlFor="totalTickets">Total Tickets</Label>
            <Input 
              id="totalTickets"
              type="number" 
              placeholder="1000"
              className="w-full"
              value={totalTickets}
              onChange={(e)=>settotalTickets(e.target.value)}
            />
          </div>

          <div className="space-y-2">
            <Label htmlFor="releaseRate">Release Rate (per second)</Label>
            <Input 
              id="releaseRate"
              type="number" 
              placeholder="5"
              className="w-full"
              value={releaseRate}
              onChange={(e)=>setreleaseRate(e.target.value)}
            />
          </div>

          <div className="space-y-2">
            <Label htmlFor="retrievalRate">Retrieval Rate (per second)</Label>
            <Input 
              id="retrievalRate"
              type="number" 
              placeholder="3"
              className="w-full"
              value={retrievalRate}
              onChange={(e)=>SetretrievalRate(e.target.value)}
            />
          </div>

          <div className="space-y-2">
            <Label htmlFor="maxCapacity">Max Capacity</Label>
            <Input 
              id="maxCapacity"
              type="number" 
              placeholder="100"
              className="w-full"
              value={maxCap}
              onChange={(e)=>setmaxCap(e.target.value)}
            />
          </div>

          <div className="space-y-2">
            <Label htmlFor="vendors">Number of Vendors</Label>
            <Input 
              id="vendors"
              type="number" 
              placeholder="3"
              className="w-full"
              value={noVendors}
              onChange={(e)=>setnoVendors(e.target.value)}
            />
          </div>

          <div className="space-y-2">
            <Label htmlFor="customers">Number of Customers</Label>
            <Input 
              id="customers"
              type="number" 
              placeholder="5"
              className="w-full"
              value={noCustomers}
              onChange={(e)=>setnoCustomers(e.target.value)}
            />
          </div>

          <Button className="w-full mt-6" size="lg"
            onClick={handleConfigureClick}
          >
            CONFIGURE
          </Button>
        </div>
      </CardContent>
    </Card>
  );
};

export default EventTicketingSystem;