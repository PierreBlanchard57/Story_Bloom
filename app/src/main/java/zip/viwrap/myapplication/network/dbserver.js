import { create, router as _router } from 'json-server';

const server = create();
const router = _router('storyDb.json'); // Your database file

// Add custom middleware for CORS
server.use((req, res, next) => {
  res.header('Access-Control-Allow-Origin', '*'); // Allow any origin
  res.header('Access-Control-Allow-Methods', 'GET, POST, PUT, DELETE, PATCH, OPTIONS');
  res.header('Access-Control-Allow-Headers', 'Origin, X-Requested-With, Content-Type, Accept');
  next();
});

server.use(router);
server.listen(3000, '0.0.0.0', () => {
  console.log('JSON Server is running on 0.0.0.0:3000');
});
