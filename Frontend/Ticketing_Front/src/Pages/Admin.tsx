import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Button } from '@/components/ui/button';
import { LineChart, Line, XAxis, YAxis, Tooltip, Legend, ResponsiveContainer } from 'recharts';
import { ScrollArea } from '@/components/ui/scroll-area';
import { Avatar, AvatarFallback } from '@/components/ui/avatar';
import { Play, Square } from 'lucide-react';
import { useState, useEffect, useRef } from 'react';

const AdminDashboard = () => {
  const [totalTickets, setTotalTickets] = useState(0);
  const [activities, setActivities] = useState([]);
  const [systemStatus, setSystemStatus] = useState('STOPPED');
  const [ticketsReleased, setTicketsReleased] = useState(0);
  const [ticketsPurchased, setTicketsPurchased] = useState(0);
  const [chartData, setChartData] = useState([
    { time: new Date().toLocaleTimeString(), customerRate: 0, vendorRate: 0 }
  ]);
  const wsRef = useRef(null);

  useEffect(() => {
    // Fetch initial total tickets
    fetch('http://localhost:8080/configure/totTickets')
      .then(response => response.text())
      .then(data => {
        setTotalTickets(Number(data).toLocaleString());
      })
      .catch(error => console.error('Error fetching total tickets:', error));

    // Set up WebSocket connection
    const ws = new WebSocket('ws://localhost:8080/websocket');
    wsRef.current = ws;

    ws.onopen = () => {
      console.log('Connected to WebSocket');
      addActivity('System', 0, 'Connected to server');
    };

    ws.onmessage = (event) => {
      try {
        const data = JSON.parse(event.data);
        console.log('Received message:', data);
        
        // Add the message to activities
        addActivity(data.actorType, data.actorId, data.message);

        // Update statistics based on the message
        if (data.actorType === 'Vendor' && data.message.includes('Released')) {
          const count = parseInt(data.message.match(/\d+/)?.[0]) || 1;
          setTicketsReleased(prev => prev + count);
          updateChart('vendorRate', count);
        } 
        else if (data.actorType === 'Customer' && data.message.includes('Retrieved')) {
          setTicketsPurchased(prev => prev + 1);
          updateChart('customerRate', 1);
        }
      } catch (error) {
        console.error('Error processing message:', error);
      }
    };

    ws.onclose = () => {
      console.log('WebSocket disconnected');
      addActivity('System', 0, 'Disconnected from server');
    };

    ws.onerror = (error) => {
      console.error('WebSocket error:', error);
      addActivity('System', 0, 'Connection error occurred');
    };

    // Cleanup on unmount
    return () => {
      if (wsRef.current) {
        wsRef.current.close();
      }
    };
  }, []);

  const addActivity = (actorType, actorId, message) => {
    setActivities(prev => [
      ...prev,
      {
        actorType,
        actorId,
        message,
        timestamp: new Date().toLocaleTimeString()
      }
    ].slice(-50)); 
  };

  const updateChart = (type, count) => {
    setChartData(prev => {
      const currentTime = new Date().toLocaleTimeString();
      const lastEntry = prev[prev.length - 1];
      const newData = {
        time: currentTime,
        customerRate: type === 'customerRate' ? 
          (lastEntry.customerRate + count) : lastEntry.customerRate,
        vendorRate: type === 'vendorRate' ? 
          (lastEntry.vendorRate + count) : lastEntry.vendorRate
      };
      return [...prev.slice(-4), newData];
    });
  };

  const handleStartBtn = async () => {
    try {
      const response = await fetch('http://localhost:8080/ticketing/start', {
        method: 'POST'
      });

      if (response.ok) {
        setSystemStatus('RUNNING');
        addActivity('System', 0, 'System started successfully');
      }
    } catch (error) {
      addActivity('System', 0, `Error starting system: ${error.message}`);
    }
  };

  const handleStopBtn = async () => {
    try {
      const response = await fetch('http://localhost:8080/ticketing/stop', {
        method: 'POST'
      });

      if (response.ok) {
        setSystemStatus('STOPPED');
        addActivity('System', 0, 'System stopped successfully');
      }
    } catch (error) {
      addActivity('System', 0, `Error stopping system: ${error.message}`);
    }
  };

  return (
    <div className="p-6 space-y-6 bg-background">
      {/* Metric Cards */}
      <div className="grid gap-4 md:grid-cols-3">
        <Card>
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">Total Tickets</CardTitle>
            <svg
              xmlns="http://www.w3.org/2000/svg"
              viewBox="0 0 24 24"
              fill="none"
              stroke="currentColor"
              strokeLinecap="round"
              strokeLinejoin="round"
              strokeWidth="2"
              className="h-4 w-4 text-muted-foreground"
            >
              <path d="M16 21v-2a4 4 0 0 0-4-4H6a4 4 0 0 0-4 4v2" />
              <circle cx="9" cy="7" r="4" />
              <path d="M22 21v-2a4 4 0 0 0-3-3.87M16 3.13a4 4 0 0 1 0 7.75" />
            </svg>
          </CardHeader>
          <CardContent>
            <div className="text-2xl font-bold">{totalTickets}</div>
            <p className="text-xs text-muted-foreground">Total pool size</p>
          </CardContent>
        </Card>

        <Card>
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">Tickets Released</CardTitle>
            <svg
              xmlns="http://www.w3.org/2000/svg"
              viewBox="0 0 24 24"
              fill="none"
              stroke="currentColor"
              strokeLinecap="round"
              strokeLinejoin="round"
              strokeWidth="2"
              className="h-4 w-4 text-muted-foreground"
            >
              <path d="M22 12h-4l-3 9L9 3l-3 9H2" />
            </svg>
          </CardHeader>
          <CardContent>
            <div className="text-2xl font-bold">{ticketsReleased}</div>
            <p className="text-xs text-muted-foreground">Total released tickets</p>
          </CardContent>
        </Card>

        <Card>
          <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle className="text-sm font-medium">Tickets Purchased</CardTitle>
            <svg
              xmlns="http://www.w3.org/2000/svg"
              viewBox="0 0 24 24"
              fill="none"
              stroke="currentColor"
              strokeLinecap="round"
              strokeLinejoin="round"
              strokeWidth="2"
              className="h-4 w-4 text-muted-foreground"
            >
              <rect width="20" height="14" x="2" y="5" rx="2" />
              <path d="M2 10h20" />
            </svg>
          </CardHeader>
          <CardContent>
            <div className="text-2xl font-bold">{ticketsPurchased}</div>
            <p className="text-xs text-muted-foreground">Total purchased tickets</p>
          </CardContent>
        </Card>
      </div>

      {/* Charts and Logs */}
      <div className="grid gap-4 md:grid-cols-2">
        <Card className="col-span-1">
          <CardHeader>
            <CardTitle>Customer vs Vendor Rate</CardTitle>
          </CardHeader>
          <CardContent>
            <div className="h-[200px]">
              <ResponsiveContainer width="100%" height="100%">
                <LineChart data={chartData}>
                  <XAxis dataKey="time" />
                  <YAxis />
                  <Tooltip />
                  <Legend />
                  <Line 
                    type="monotone" 
                    dataKey="customerRate" 
                    stroke="#8884d8" 
                    strokeWidth={2}
                    name="Customer Retrieval Rate" 
                  />
                  <Line 
                    type="monotone" 
                    dataKey="vendorRate" 
                    stroke="#82ca9d" 
                    strokeWidth={2}
                    name="Vendor Release Rate"
                  />
                </LineChart>
              </ResponsiveContainer>
            </div>
          </CardContent>
        </Card>

        <Card className="col-span-1">
          <CardHeader>
            <CardTitle className="flex justify-between items-center">
              Activity Log
              <span className={`text-sm px-2 py-1 rounded ${
                systemStatus === 'RUNNING' ? 'bg-green-100 text-green-800' : 'bg-red-100 text-red-800'
              }`}>
                {systemStatus}
              </span>
            </CardTitle>
          </CardHeader>
          <CardContent>
            <ScrollArea className="h-[200px] w-full rounded-md border p-4">
              <div className="space-y-4">
                {activities.map((activity, index) => (
                  <div key={index} className="flex items-center justify-between">
                    <div className="flex items-center space-x-4">
                      <Avatar className="h-8 w-8">
                        <AvatarFallback>
                          {activity.actorType === 'Vendor' ? 'V' : 
                           activity.actorType === 'Customer' ? 'C' : 'S'}
                          {activity.actorId}
                        </AvatarFallback>
                      </Avatar>
                      <div>
                        <p className="text-sm font-medium">
                          {activity.actorType} {activity.actorId}
                        </p>
                        <p className="text-xs text-muted-foreground">
                          {activity.message}
                        </p>
                      </div>
                    </div>
                    <span className="text-xs text-muted-foreground">
                      {activity.timestamp}
                    </span>
                  </div>
                ))}
              </div>
            </ScrollArea>
          </CardContent>
        </Card>
      </div>

      {/* Control Buttons */}
      <div className="flex justify-center gap-4">
        <Button 
          size="lg" 
          className="w-32 h-12 bg-[#22c55e] hover:bg-[#15803d]" 
          variant="default" 
          onClick={handleStartBtn}
          disabled={systemStatus === 'RUNNING'}
        >
          <Play className="mr-2 h-5 w-5" /> Start
        </Button>
        <Button 
          size="lg" 
          className="w-32 h-12" 
          variant="destructive" 
          onClick={handleStopBtn}
          disabled={systemStatus === 'STOPPED'}
        >
          <Square className="mr-2 h-5 w-5" /> Stop
        </Button>
      </div>
    </div>
  );
};

export default AdminDashboard;