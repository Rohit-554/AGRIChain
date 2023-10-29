const { PrismaClient } = require('@prisma/client');
const prisma = new PrismaClient();
const auth = async (data) => {
  try {
    // const { phoneNo } = req;

    const existingUser = await prisma.user.findFirst();
    console.log(data.phoneNo,"jczxvgfhjkl;");
    // console.log(req,"hggfdxfghjj");

    if (existingUser) {
      console.log('User login');
      console.log(req);

      return {...existingUser};
    } else {
      const newUser = await prisma.user.create({
        data: {
          phoneNo,
        },
      });

      console.log('User signup');
      return newUser;
    }
  } catch (error) {
    console.error(error);
    return null; // Return null or handle the error as needed
  }
};

module.exports = auth;
