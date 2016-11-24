using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Controls;

namespace PIGOU.Controller
{
    public abstract class BaseController
    {
        protected Page ihm;

        public BaseController(Page ihm)
        {
            this.ihm = ihm;
        }
    }
}
